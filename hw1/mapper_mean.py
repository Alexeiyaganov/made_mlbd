#!/usr/bin/python3

import sys
import math
# import csv

ck = 0
prices = []
for line in sys.stdin: #for line in csv.reader(sys.stdin, delimiter=","):
    line = line.strip()
    price = int(line.split(',')[-1])
    ck += 1
    prices.append(price)

mk = sum(prices)/ck if ck else 0
print(ck, mk)