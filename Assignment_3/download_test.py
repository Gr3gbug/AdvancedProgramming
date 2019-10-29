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
    # TODO loggin.info()
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
            for csvFileName in listName:
                if (gElement == csvFileName):
                    # Aggiungo la lista di file da scaricare selezionadolo su
                    # base indice
                    tmpFile = listTest[listName.index(gElement)].split(':')
                    if(file_extension == '.py') or (file_extension == '.hs'):
                        for element in tmpFile:
                            print 'Downloading Haskell/Python test ('+element.replace(" ", "")+')'+' for: ' + file_name               
                            res, header = urlretrieve((url+'/' + element.replace(" ", "")), (rootPath+root[1:]+'/' + element.replace(" ", "")))
                            # logging.info('Downloaded file: '+element.replace(" ", ""))
                            # logging.info('File downloaded in: '+res)
                            print '---> Done'
                    else:
                        if (file_extension == '.java'):
                            for element in tmpFile:
                                file = open(rootPath+root[1:]+'/'+gElement, "r")
                                for line in file:
                                    if 'package' in line:
                                        # Ho trovato 'package' Devo scaricare, ma nella cartella superiore
                                        print 'Trovato package Java File: '+file_name
                                        tmpPath = root[1:].split('/')                                        
                                        print 'Downloading java test ('+ element.replace(" ", "")+')'+' for: ' + file_name                                        
                                        res, header = urlretrieve((url+'/'+element.replace(" ", "")), (rootPath+'/'+tmpPath[len(tmpPath)-2]+'/'+element.replace(" ", "")))                                        
                                        # logging.info('Downloaded file: '+element.replace(" ", ""))
                                        # logging.info('File downloaded in: '+res)
                                        print '---> Done'
                                        break
                                    else:
                                        # Vuol dire che non ho trovato package, lo scarico diretto
                                        print 'Downloading java test ('+element.replace(" ", "")+')'+' for: ' + file_name
                                        res, header = urlretrieve((url+'/' + element.replace(" ", "")), (rootPath+root[1:]+'/' + element.replace(" ", "")))
                                        # logging.info('Downloaded file: '+element.replace(" ", ""))
                                        # logging.info('File downloaded in: '+res)
                                        print '---> Done'
                                        break                                        
                                file.close()

download_test(rootPath, url)
