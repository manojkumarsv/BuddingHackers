# -*- coding: utf-8 -*-
"""
Created on Sat Jul 20 13:32:45 2019

@author: Hack5CHETeam4
"""

import os.path as path
import numpy as np
import pandas as pd

from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.metrics import accuracy_score
from sklearn import linear_model

import Logger
inst_log = Logger.Logger()
fp_logger = inst_log.setLoggerConfig()

homeDir = path.abspath(path.join(__file__,"../../../../"))
datafile = homeDir + "/data//fwdata.csv"

class foodPred:
	
	def __init__(self):
		fp_logger.debug("food predictor class instantiated")
	
	def loadDataset(self,filename, training_features, target, split):
		
		fp_logger.debug('loading dataset')
		df= pd.read_csv(filename,header=0)
		#column class
		df['days'] = pd.to_numeric(df['days'],errors='coerce').fillna(0).astype(np.int64)
		df['occasion'] = pd.to_numeric(df['occasion'],errors='coerce').fillna(0).astype(np.int64)
		df=df.replace('?',np.nan)
		return train_test_split(df[training_features], df[target], train_size=split)
	
	def predictFood(self,model,FoodPersonData):
		FoodPerson = pd.DataFrame(FoodPersonData)
		FoodPerson['days'] = pd.to_numeric(FoodPerson['days'],errors='coerce').fillna(0).astype(np.int64)
		FoodPerson['occasion'] = pd.to_numeric(FoodPerson['occasion'],errors='coerce').fillna(0).astype(np.int64)
		FoodPerson['surplus']=[0]
		pf = model.predict(FoodPerson)
		fp_logger.info("Predicted food person: "+str(pf) )
		print(pf)
		return (pf)
	
	def createModel(self):
		# prepare data
		training_features = ['days','occasion','attendance','surplus']
		target = 'attended'
	
		train_size = 0.7
		train_x, test_x, train_y, test_y = self.loadDataset(datafile, training_features,target,train_size)
	
	    ##linear regression
		model = linear_model.LinearRegression()
		model.fit(train_x, train_y)	    
		print(model)
		
		# make predictions
		expected = test_y
		predicted = model.predict(test_x)
		
		print("\n")
		#	# The coefficients
		print('Coefficients: \n', model.coef_)
		print('Intercept:\n',model.intercept_)
		# The mean squared error
		print("Mean squared error: %.2f"
		      % mean_squared_error(test_y, predicted))
		# Explained variance score: 1 is perfect prediction
		print('R Square score: %.2f' % r2_score(test_y, predicted))
		accuracy = model.score(test_x,test_y)
		print("Accuracy: ", accuracy*100,'%')
		print("\n")
		return model

	
FoodPersonData = {'days':['Friday'],'occasion':['NonVeg'],'attendance':[47]}	

fp = foodPred()
model = fp.createModel()
print(fp.predictFood(model,FoodPersonData))
