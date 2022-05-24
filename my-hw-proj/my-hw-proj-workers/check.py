import git
import subprocess


def default_check_func():
    """
    Assume that we have already cloned our repository in the current directory
    :return:
    """
    proc = subprocess.run(["./gradlew", "test"], text=True, stderr=subprocess.STDOUT)
    if proc.returncode != 0:
        mark = 0
    else:
        mark = 1
    return mark, proc.stdout


def clone_and_check(repository_link, check_func):
    git.Git('.').clone(repository_link)
    return check_func()

