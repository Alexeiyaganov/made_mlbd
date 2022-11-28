import pandas as pd

df = pd.read_csv('AB_NYC_2019.csv')
print(df['price'].mean(), df['price'].std()**2)