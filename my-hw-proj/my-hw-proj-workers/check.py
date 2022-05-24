import os.path
import subprocess
import git


def default_check_func(root):
    """
    Assume that we have already cloned our repository in the current directory
    :return:
    """
    try:
        proc = subprocess.run(
            ["./gradlew", "test"],
            cwd=f"./{root}",
            text=True,
            capture_output=True
        )
        if proc.returncode != 0:
            mark = 0
        else:
            mark = 1

        return mark, proc.stdout
    except Exception as e:
        return 0, str(e)


def clone_and_check(repository_link, check_func):
    cwd = f"./{repository_link.rpartition('/')[2]}"
    if os.path.exists(cwd):
        if os.path.isdir(cwd):
            os.rmdir(cwd)
        else:
            os.remove(cwd)

    git.Git('.').clone(repository_link)
    return check_func(cwd)
