# I have used the 'os' module, which has functions to interact with the
# Operating System. Ready Function of module:
# os.chdir() - to change working directory,
# os.getcwd() - to get working directory,
# os.rename() - to rename a file
import os

confirm_environment = True
while confirm_environment:
    input_Path = raw_input("Enter path what you scan: ")
    print("your insert path is: " + input_Path)
    if raw_input("Path is correct ? (Yes/no)") != "no":
        confirm_environment = False
        os.chdir(input_Path)

print "Working directory is: " + os.getcwd()
for root, dirs, files in os.walk(input_Path, topdown=False):
    for name in files:
        # print "\nComplete path: " + (os.path.join(root, name))
        file_name, file_extension = os.path.splitext(name)
        # print "File_extension: " + file_name + file_extension
        os.chdir(root)
        if file_extension == ".raj":
            print "Jar Found :)"
            print os.getcwd()
            os.rename(file_name+file_extension, file_name+".jar")
        else:
            print "Nothing Found"
