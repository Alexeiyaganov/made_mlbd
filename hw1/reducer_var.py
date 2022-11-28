#!/usr/bin/python3

import sys

c, m, v = 0, 0, 0
for line in sys.stdin:
    line = line.strip()
    ck, mk, vk = line.split()
    ck, mk, vk = int(ck), float(mk), float(vk)
    v =  (((c * v) + (ck * vk)) / (c+ck)) + ((c * ck) * ((mk - m) / (c+ck))**2)
    m = ((m*c)+(mk*ck))/(c+ck)
    c += ck

print(c, m, v)