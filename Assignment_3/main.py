import os
import sys
import csv
import shutil
import logging

if sys.version_info[0] >= 3:
    from urllib.request import urlretrieve
else:    
    # Note that this might need an update when Python 4
    # might be around one day
    from urllib import urlretrieve

menu_actions = {}


# Main menu
def main_menu():
    logging.basicConfig(filename='myapp.log',
                        level=logging.INFO, format='%(asctime)s %(message)s')
    os.system('clear')

    print("*** Welcome ***\n")
    print("Please choose the script you want to start:")
    print("1. raj2jar")
    print("2. Collect Sources")
    print("3. Rebuild Packages")
    print("4. Download Tests")
    print("\n0. Quit")
    choice = raw_input(" >>  ")
    exec_menu(choice)

    return


# Execute menu
def exec_menu(choice):
    os.system('clear')
    ch = choice.lower()
    if ch == '':
        menu_actions['main_menu']()
    else:
        try:
            menu_actions[ch]()
        except KeyError:
            print "Invalid selection, please try again.\n"
            menu_actions['main_menu']()
    return


# Back to main menu
def back():
    menu_actions['main_menu']()


# Exit program
def exit():
    sys.exit()


# Insertion of path
def InsertPath():
    logging.info('CALL insert_path function')
    confirm_path = True
    while confirm_path:
        rootPath = raw_input("Enter absolute path in the local file system: ")
        print("your insert path is: " + rootPath)
        if raw_input("Path is correct ? (yes/no): ") != "no":
            confirm_path = False
            os.chdir(rootPath)
            logging.info('Working directory is: ' + rootPath)

    return rootPath


# Raj2jar Function
def raj2jar():
    print("*** Starting execution of raj2jar ***\n")
    logging.info("*** Starting execution of raj2jar ***")
    input_Path = InsertPath()
    found = False
    for root, dirs, files in os.walk(input_Path, topdown=False):
        for name in files:
            file_name, file_extension = os.path.splitext(name)
            os.chdir(root)
            if file_extension == ".raj":
                logging.info(" !!! Jar Found !!!")
                logging.info("-- Complete path of file is: " +
                             (os.path.join(root, name)))
                logging.info("--- File name is: " +
                             file_name + file_extension)
                os.rename(file_name+file_extension, file_name+".jar")
                found = True
    if found is False:
        logging.info('Nothing found')

    return


# Collect Sources Function
def collect_sources():
    print("*** Starting execution of collect_sources ***\n")
    logging.info("*** Starting execution of collect_sources ***")
    rootPath = InsertPath()
    confirm_name = True
    while confirm_name:
        sourceName = raw_input("Enter name of source file: ")
        print("Your insert name is: " + sourceName)
        if raw_input("Name is correct ? (Yes/no): ") != "no":
            confirm_name = False
    os.chdir(rootPath)  # Change the directory
    os.mknod((sourceName+".txt"))  # Create source file
    logging.info(sourceName+".txt" + " has been created")
    file = open(sourceName+".txt", "w")  # Open file before starting loop
    # start looping from the selected directory
    for root, dirs, files in os.walk(".", topdown=False, followlinks=False):
        for name in files:
            file_name, file_extension = os.path.splitext(name)
            if (file_extension == ".java") or (file_extension == ".hs") or (file_extension == ".py"):
                file.write(os.path.join(root[1:], name) + "\n")
    file.close()

    return


# Definition of Rebuild Package function
def rebuild_packages():
    print("*** Starting execution of rebuild_packages ***\n")
    logging.info("*** Starting execution of rebuild_packages ***")
    rootPath = InsertPath()    
    for root, dirs, files in os.walk(".", topdown=False, followlinks=False):
        for name in files:
            file_name, file_extension = os.path.splitext(name)
            # Mi muovo nella relativa directory su cui devo lavorare
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
                        logging.info(" - File found in: " + (os.path.join(root[1:])))
                        # in 'line' ho il contenuto della linea package
                        matchedLine = line
                        # Sanitize the keyword
                        pkgName = matchedLine.replace("package", "").replace(
                            ";", "").replace(" ", "").replace("\n", "")
                        dirName = os.path.basename(os.getcwd())
                        # print os.getcwd()
                        if(pkgName == dirName):
                            logging.info(" -- The package name and directory correspond")
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
                                logging.info(" --- Exisent subdirectory found! File moved in: " + 
                                             tmpAbsSubPath[(tmpSubPath.index(pkgName))])
                            # Otherwise, i'll create the subDirectory, and
                            # then i'll move the file into
                            else:
                                # Devo creare la directory con lo stesso nome
                                os.mkdir(os.getcwd()+"/"+pkgName, 0755)
                                shutil.move(os.getcwd()+"/"+name,
                                            os.getcwd()+"/"+pkgName)
                                logging.info(" --- SubDir Created! File moved in")
                        break
        os.chdir(rootPath)

    return


# Function download_tests
def download_tests():
    print("*** Starting execution of download_tests ***\n")
    logging.info("*** Starting execution of download_tests ***")
    url = "http://pages.di.unipi.it/corradini/Didattica/AP-18/PROG-ASS/03/Test"
    fileName = "AP_TestRegistry.csv"
    # Scelgo se inserire una nuova URL o tenere quella di default
    if (raw_input("Actually URL is: " + url+"\nDo you want change it or not? (yes/no): ") != "no"):
        url = (raw_input("Insert new URL: "))
        logging.info("Working URL is: " + url)
    else:
        logging.info("Working URL is: " + url)
    rootPath = InsertPath()
    # Download del file
    res, header = urlretrieve((url+"/"+fileName), (rootPath+"/"+fileName))
    logging.info(fileName+' downloaded in: ' + res)
    listName = [] # support variable
    listTest = [] # support variable
    # Open and reading of CSV file
    with open(fileName, 'r') as csvFile:
        reader = csv.DictReader(csvFile)
        for row in reader:
            listName.append(row[row.keys()[2]])
            listTest.append(row[row.keys()[1]])
    # start looping from the selected directory
    for root, dirs, files in os.walk(".", topdown=False, followlinks=False):
        for gElement in files:
            file_name, file_extension = os.path.splitext(gElement)
            for csvFileName in listName:
                if (gElement == csvFileName):
                    # Aggiungo la lista di file da scaricare selezionadolo su
                    # base indice
                    tmpFile = listTest[listName.index(gElement)].split(':')
                    if(file_extension == '.py') or (file_extension == '.hs'):
                        for element in tmpFile:
                            print 'Downloading Haskell/Python test ('+element.replace(" ", "")+')'+' for: ' + file_name
                            res, header = urlretrieve(
                                (url+'/' + element.replace(" ", "")), 
                                (rootPath+root[1:]+'/' + element.replace(" ", "")))
                            logging.info(element.replace(" ", "")+' downloaded in: '+res)
                            print '---> Done'
                    else:
                        if (file_extension == '.java'):
                            for element in tmpFile:
                                file = open(
                                    rootPath+root[1:]+'/'+gElement, "r")
                                for line in file:
                                    if 'package' in line:  # Package found ->
                                        # --> download in parent
                                        tmpPath = root[1:].split('/')
                                        print 'Downloading java test (' + element.replace(" ", "")+')'+' for: ' + file_name
                                        res, header = urlretrieve(
                                            (url+'/' + element.replace(" ", "")
                                             ), (rootPath+root[1:]+'/'
                                                 + element.replace(" ", "")))
                                        logging.info(element.replace(" ", "")
                                                     + ' downloaded in: '+res)
                                        print '---> Done'
                                        break
                                    else:  # Package not found ->
                                        # download in directory
                                        print 'Downloading java test ('+element.replace(" ", "")+')'+' for: ' + file_name
                                        res, header = urlretrieve(
                                            (url+'/' + element.replace(" ", "")
                                             ), (rootPath+root[1:]+'/'
                                                 + element.replace(" ", "")))
                                        logging.info(element.replace(" ", "")
                                                     + ' downloaded in: '+res)
                                        print '---> Done'
                                        break
                                file.close()

    return


# Menu definition
menu_actions = {
    'main_menu': main_menu,
    '1': raj2jar,
    '2': collect_sources,
    '3': rebuild_packages,
    '4': download_tests,
    '9': back,
    '0': exit,
}


# Main Program
if __name__ == "__main__":
    # Launch main menu
    main_menu()
