#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys
import csv
import codecs

def readcsv(filename):
	with open(filename, 'rb') as f:
		reader = csv.reader(f)
		recipes = []
		counter = 20
		for row in reader:
			counter = counter + 1
			steps = []
			steps.append(counter)
			steps.append(row[2])
			recipes.append(steps)

	return recipes

def writecsv(output, recipes):
	with open(output, 'wb') as f:
		writer = csv.writer(f)
		for row in recipes:
			writer.writerow(row)

if __name__ == "__main__":
	input = sys.argv[1]
	output = sys.argv[2]
	recipes = readcsv(input)
	writecsv(output, recipes)