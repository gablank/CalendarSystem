__author__ = 'anders'

import os
import os.path


dirtocheck = os.getcwd()

for root, _, files in os.walk(dirtocheck):
    for f in files:
        fullpath = os.path.join(root, f)
        extension = fullpath.split('.')

        if extension[-1] == 'java':
            print(fullpath)
            f = open(fullpath, 'r')
            lines = []
            for line in f:
                newLine = ""
                upperNext = False
                for i in range(len(line)):
                    if line[i] == '_' and i + 1 < len(line) and line[i + 1].lower() == line[i + 1]:
                        upperNext = True
                    else:
                        if upperNext:
                            newLine += line[i].upper()
                            upperNext = False
                        else:
                            newLine += line[i]
                lines.append(newLine)
            f.close()
            f = open(fullpath, 'w')
            for line in lines:
                f.write(line)
            f.close()



