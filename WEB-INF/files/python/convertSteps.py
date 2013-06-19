#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys
import csv
import codecs

def readcsv(filename):
	with open(filename, 'rb') as f:
		reader = csv.reader(f)
		id = 0
		rows = []
		data = []
		counter = 20

		for row in reader:
			if id != row[0]:
				id = row[0]
				counter = counter + 1

			data.append(counter)
			data.append(row[1])
			data.append(row[3])
			rows.append(data)
			data = []

	return rows

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