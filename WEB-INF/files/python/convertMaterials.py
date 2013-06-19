#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys
import csv
import re

def readcsv(filename):
	with open(filename, 'rU') as f:
		reader = csv.reader(f)
		id = 0
		rows = []
		data = []

		for row in reader:
			data.append(row[0])
			data.append(row[1])
			data.append(row[2])
			if (row[3] == ''):
				row[3] = 0
			data.append(row[3])
			data.append(row[4])
			rows.append(data)
			data = []

	return rows

def writecsv(output, rows):
	with open(output, 'wb') as f:
		writer = csv.writer(f)
		for row in rows:
			writer.writerow(row)

if __name__ == "__main__":
	input = sys.argv[1]
	output = sys.argv[2]
	rows = readcsv(input)
	writecsv(output, rows)