import sys
import getsize
import os
from os.path import join

rootPath = sys.argv[1]
sourceName = sys.argv[2] + ".txt"


def collect_sources(rootPath, sourceName):
    os.chdir(rootPath)  # Change the directory
    print "Selected path is: " + os.getcwd()
    os.mknod(sourceName)  # Create source file
    print sourceName + " has been created"  # Show the name of source file
    file = open(sourceName, "w")  # Open file before starting loop
    # start looping from the selected directory
    for root, dirs, files in os.walk(".", topdown=False, followlinks=False):
        for name in files:
            file_name, file_extension = os.path.splitext(name)
            if (file_extension == ".java") or (file_extension == ".hs") or (file_extension == ".py"):
                file.write(os.path.join(root[1:], name) + "\n")
    file.close()

collect_sources(rootPath, sourceName)
