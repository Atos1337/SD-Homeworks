import json
import sys

import pika


def main():
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))

    channel_recv = connection.channel()
    channel_send = connection.channel()

    channel_recv.queue_declare(queue="task_queue")
    channel_send.queue_declare(queue="res_queue")

    def callback(ch, method, properties, body):
        print(" [x] Received %r" % str(body))
        submission = json.loads(str(body))

        submissionResult = {
            "submissionId": submission["submissionId"],
            "mark": 10,
            "comment": "harosh"
        }
        channel_send.basic_publish(
            exchange='',
            routing_key='res_queue',
            body=bytes(json.dumps(submissionResult))
        )

    channel_recv.basic_consume(queue='task_queue', on_message_callback=callback, auto_ack=True)

    print(' [*] Waiting for submissions. To exit press CTRL+C')
    channel_recv.start_consuming()


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Interrupted')
        sys.exit(0)
