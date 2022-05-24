import subprocess

import git


def default_check_func(root):
    """
    Assume that we have already cloned our repository in the current directory
    :return:
    """
    proc = subprocess.run(
        [f"./gradlew", "test"],
        cwd=f"./{root}",
        text=True,
        #stderr=subprocess.STDOUT,
        capture_output=True
    )
    if proc.returncode != 0:
        mark = 0
    else:
        mark = 1
    print("proc.stdout !!!!!!-------------------")
    print(proc.stdout)
    print(type(proc.stdout))

    return mark, proc.stdout


def clone_and_check(repository_link, check_func):
    git.Git('.').clone(repository_link)
    return check_func(repository_link.rpartition('/')[2])
