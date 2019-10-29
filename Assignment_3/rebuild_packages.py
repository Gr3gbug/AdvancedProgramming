import os
import sys
import shutil

path = sys.argv[1]


def rebuild_packages(rootPath):
    os.chdir(rootPath)
    print "Currently directory is: " + os.getcwd()
    for root, dirs, files in os.walk(".", topdown=False, followlinks=False):
        for name in files:
            file_name, file_extension = os.path.splitext(name)
            # Mi muovo nella relativa directory
            os.chdir(rootPath+os.path.join(root[1:]))
            # stringa di cui voglio fare il match
            stringToMatch = 'package'
            # Variabile di appoggio nel caso in cui trovo la stringa che cerco
            matchedLine = ''
            tmpSubPath = []
            tmpAbsSubPath = []
            # get line
            with open(name, 'rt') as in_file:
                for line in in_file:
                    # Check if file is ".java" and contain "package" keyword
                    if (file_extension == ".java") and (stringToMatch in line):
                        print "\n - File found in: " + (os.path.join(root[1:]))
                        # in 'line' ho il contenuto della linea package
                        matchedLine = line
                        # Sanitize
                        pkgName = matchedLine.replace("package", "").replace(
                            ";", "").replace(" ", "").replace("\n", "")
                        dirName = os.path.basename(os.getcwd())
                        # print os.getcwd()
                        if(pkgName == dirName):
                            print " -- The package name and directory correspond"
                        if(pkgName != dirName):
                            for path, subdirs, files in os.walk(os.getcwd()):
                                tmpSubPath.append(os.path.basename(path))
                                tmpAbsSubPath.append((path))
                            # If exist a Subdirectory from the path with
                            # 'pkgName' --> move it
                            if pkgName in tmpSubPath:
                                shutil.move(
                                    (os.getcwd()+"/"+name),
                                    tmpAbsSubPath[(tmpSubPath.index(pkgName))])
                                print " --- Subdirectory found! File moved into: " + tmpAbsSubPath[(tmpSubPath.index(pkgName))]
                            # Otherwise, i'll create the subDirectory, and
                            # then i'll move the file into
                            else:
                                # Devo creare la directory con lo stesso nome
                                os.mkdir(os.getcwd()+"/"+pkgName, 0755)
                                shutil.move(os.getcwd()+"/"+name,
                                            os.getcwd()+"/"+pkgName)
                                print " --- SubDir Created! File moved in :)"
                        break
        os.chdir(rootPath)


rebuild_packages(path)
