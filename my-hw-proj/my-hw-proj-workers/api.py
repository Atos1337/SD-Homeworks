import configparser
import json
import sys

import pika

from check import default_check_func, clone_and_check

DEFAULT = 'DEFAULT'
HOST = 'host'
PORT = 'port'
RECV_QUEUE = 'recv_queue'
SEND_QUEUE = 'send_queue'


def run_worker_internal(check_func=default_check_func):
    """
    Run worker with check_func
    :param check_func: some func that should be called after we clone repository to check submission
    """
    config = configparser.ConfigParser()
    config.read('config.ini')
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(host=config[DEFAULT][HOST], port=int(config[DEFAULT][PORT]))
    )

    channel_recv = connection.channel()
    channel_send = connection.channel()

    channel_recv.queue_declare(queue=config[DEFAULT][RECV_QUEUE])
    channel_send.queue_declare(queue=config[DEFAULT][SEND_QUEUE])

    def callback(ch, method, properties, body):
        body = body.decode("utf-8")
        print(" [x] Received %r" % str(body))
        submission = json.loads(str(body))

        print("Start checking")
        mark, output = clone_and_check(submission["solution"], check_func)

        submissionResult = {
            "submissionId": submission["id"],
            "mark": mark,
            "comment": output if output is not None else ""
        }

        print("Finish checking")
        print(submissionResult)

        channel_send.basic_publish(
            exchange='',
            routing_key=config[DEFAULT][SEND_QUEUE],
            body=bytes(json.dumps(submissionResult), "utf-8")
        )

    channel_recv.basic_consume(queue=config[DEFAULT][RECV_QUEUE], on_message_callback=callback, auto_ack=True)

    print(' [*] Waiting for submissions. To exit press CTRL+C')
    channel_recv.start_consuming()


def run_worker(check_func=default_check_func):
    try:
        run_worker_internal(check_func)
    except KeyboardInterrupt:
        print('Interrupted')
        sys.exit(0)
