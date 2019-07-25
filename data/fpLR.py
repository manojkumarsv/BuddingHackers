# -*- coding: utf-8 -*-
"""
Created on Sat Jul 20 13:32:45 2019

@author: Hack5CHETeam4
"""

 
import numpy as np
import pandas as pd

from sklearn.model_selection import train_test_split

from sklearn import metrics
from sklearn import datasets, linear_model
from sklearn.metrics import mean_squared_error, r2_score

df=""
def loadDataset(filename, training_features, target, split):
	global df
	df= pd.read_csv(filename,header=0)
	#column class
	d=df.dtypes
	print(d)
	df['days'] = pd.to_numeric(df['days'],errors='coerce').fillna(0).astype(np.int64)
	df['occasion'] = pd.to_numeric(df['occasion'],errors='coerce').fillna(0).astype(np.int64)
	print(df.dtypes)
#	print (df['days'])
	

	# chechk null values
	#print(df.isnull().sum())
	df=df.replace('?',np.nan)
	
	return train_test_split(df[training_features], df[target], train_size=split)

def predictFood(model,FoodPersonData):
	FoodPerson = pd.DataFrame(FoodPersonData)
	FoodPerson['days'] = pd.to_numeric(FoodPerson['days'],errors='coerce').fillna(0).astype(np.int64)
	FoodPerson['occasion'] = pd.to_numeric(FoodPerson['occasion'],errors='coerce').fillna(0).astype(np.int64)
	print(model.predict(FoodPerson))

def main():
	# prepare data
	training_features = ['days','occasion','attendance','surplus']
	target = 'attended'

	train_size = 0.75
	train_x, test_x, train_y, test_y = loadDataset('fwdata.csv', training_features,target,train_size)

    
    ##linear regression
	model = linear_model.LinearRegression()
	model.fit(train_x, train_y)
    
	print(model)
	# make predictions
	expected = test_y
	predicted = model.predict(test_x)
	print ("-------predicted--------")
	print(expected)
	print(predicted)
	
	#	# The coefficients
	print('Coefficients: \n', model.coef_)
	print('Intercept:\n',model.intercept_)
	# The mean squared error
	print("Mean squared error: %.2f"
	      % mean_squared_error(test_y, predicted))
	# Explained variance score: 1 is perfect prediction
	print('R Square score: %.2f' % r2_score(test_y, predicted))
	print("-----\n")
	
	FoodPersonData = {'days':['Wednesday'],'occasion':['NonVeg'],'attendance':[147],
	      'surplus':[0]}
	
	predictFood(model,FoodPersonData)


    
	
main()