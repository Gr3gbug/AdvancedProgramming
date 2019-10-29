import os
import sys
import csv
# REMEMBER!!! All print must become logging.info()
if sys.version_info[0] >= 3:
    from urllib.request import urlretrieve
else:
    # Not Python 3 - today, it is most likely to be Python 2
    # But note that this might need an update when Python 4
    # might be around one day
    from urllib import urlretrieve

fileName = "AP_TestRegistry.csv"
rootPath = sys.argv[1]
if len(sys.argv) > 2:
    url = sys.argv[2]
else:
    url = "http://pages.di.unipi.it/corradini/Didattica/AP-18/PROG-ASS/03/Test"


def download_test(rootPath, url):
    res, header = urlretrieve((url+"/"+fileName), (rootPath+"/"+fileName))
    print "Downloaded in: " + res
    os.chdir(rootPath)
    listName = []
    listTest = []
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
            tmpFile = []
            if (gElement in listName):
                # Aggiungo la lista di file da scaricare
                # selezionadolo su base indice
                tmpFile = listTest[listName.index(gElement)].split(':')
                if(file_extension == '.py') or (file_extension == '.hs'):
                    print "Caso py/hs: "
                    print tmpFile
                    for element in tmpFile:
                        print '\nDownloading Haskell/Python File'
                        print file_name
                        res, header = urlretrieve((url+'/' + element.replace(" ", "")), (rootPath+root[1:]+'/'+element.replace(" ", "")))
    for root, dirs, files in os.walk(".", topdown=False, followlinks=False):
        for gElement in files:
            file_name, file_extension = os.path.splitext(gElement)
            tmpFile = []
            if (gElement in listName):
                # Aggiungo la lista di file da scaricare
                # selezionadolo su base indice
                if (file_extension == '.java'):
                    tmpFile = listTest[listName.index(gElement)].split(':')
                    print "Caso Java: "
                    print tmpFile
                    for jElement in tmpFile:
                        file = open(rootPath+root[1:]+'/'+gElement, "r")
                        for line in file:
                            if 'package' in line:
                                # Devo scaricare ma alla cartella superiore
                                print '\nTrovato package Java File :) '
                                print file_name
                                print(rootPath+root[1:])
                                break
                            else:
                                # Vuol dire che non ho trovato package, scarico diretto
                                print '\nTrovato simple Java File'
                                print file_name
                                break
                                # res, header = urlretrieve((url+'/' + jElement.replace(" ", "")), (rootPath+root[1:]+'/'+jElement.replace(" ", "")))
                        file.close()


download_test(rootPath, url)
