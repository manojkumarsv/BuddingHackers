# -*- coding: utf-8 -*-
"""
Created on Sat Jul 20 13:32:45 2019

@author: Hack5CHETeam4
"""

import os.path as path
import numpy as np
import pandas as pd
import sys

from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.metrics import accuracy_score
from sklearn import linear_model
import matplotlib.pyplot as plt 

import Logger
inst_log = Logger.Logger()
fp_logger = inst_log.setLoggerConfig()

homeDir = path.abspath(path.join(__file__,"../../../../"))
dataPath = path.abspath(path.join(__file__,"../../"))
datafile = dataPath + "/data//fwdata.csv"

class foodPred:
	
	def __init__(self):
		fp_logger.debug("food predictor class instantiated")
	
	def loadDataset(self,filename, training_features, target, split):
		
		fp_logger.info('loading dataset and preparing training and test set')
		df= pd.read_csv(filename,header=0)
		#column class
		df['days'] = pd.to_numeric(df['days'],errors='coerce').fillna(0).astype(np.int64)
		df['occasion'] = pd.to_numeric(df['occasion'],errors='coerce').fillna(0).astype(np.int64)
		df=df.replace('?',np.nan)
		
		
		return train_test_split(df[training_features], df[target], train_size=split)
	
	def predictFood(self,model,FoodPersonData):
		fp_logger.info('predicting the food person data using the prepared model')
		FoodPerson = pd.DataFrame(FoodPersonData)
		FoodPerson['days'] = pd.to_numeric(FoodPerson['days'],errors='coerce').fillna(0).astype(np.int64)
		FoodPerson['occasion'] = pd.to_numeric(FoodPerson['occasion'],errors='coerce').fillna(0).astype(np.int64)
		FoodPerson['surplus']=[0]
		pf = model.predict(FoodPerson)
		fp_logger.info("Predicted food person: "+str(pf) )
		return (pf)
	
	def drawGraph(self,x,y):
		
		
		fp_logger.debug("Plotting Graph")
		left = list(range(1, len(x)+1))

		
		plt.plot(left,x,label="Attendance")
		plt.plot(left,y,label="Predicted")
		
		# naming the x axis 
		plt.xlabel('Different Dates') 
		# naming the y axis 
		plt.ylabel('Attendance') 
		# giving a title to my graph 
		plt.title('Attendance VS Predicted Food person!') 
		  
		# show a legend on the plot 
		plt.legend() 
		  
		# function to show the plot 		
		plt.draw()
		fp_logger.debug("Saving Graph")
		plt.savefig(dataPath + '/data//predictedFoodPerson.png', dpi=100)
		plt.savefig(dataPath + '/data//predictedFoodPerson.pdf', dpi=100)
		#plt.show()
	
	def createModel(self):
		# prepare data
		training_features = ['days','occasion','attendance','surplus']
		target = 'attended'
	
		train_size = 0.7
		train_x, test_x, train_y, test_y = self.loadDataset(datafile, training_features,target,train_size)
	
	    ##linear regression
		model = linear_model.LinearRegression()
		model.fit(train_x, train_y)	    
		fp_logger.debug(model)
		
		# make predictions
		expected = test_y
		predicted = model.predict(test_x)
		
		#	# The coefficients
		fp_logger.debug('Coefficients: \n' + str(model.coef_))
		fp_logger.debug('Intercept:\n' + str(model.intercept_))
		# The mean squared error
		fp_logger.debug("Mean squared error: %.2f" % mean_squared_error(test_y, predicted))
		# Explained variance score: 1 is perfect prediction
		fp_logger.debug('R Square score: %.2f' % r2_score(test_y, predicted))
		accuracy = model.score(test_x, test_y)
		fp_logger.debug("Accuracy: " + str( accuracy*100)+ str('%'))
		
		
		self.drawGraph(test_x['attendance'],test_y)
		
		
		return model

def main():
	
	FoodPersonData = {}
	fp = foodPred()
	model = fp.createModel()
	
	if ((len(sys.argv)>1) and (len(sys.argv)==2)):
		FoodPersonData['days'] = [sys.argv[1]]
	elif ((len(sys.argv)>1) and (len(sys.argv)==3)):
		FoodPersonData['days'] = [sys.argv[1]]
		FoodPersonData['occasion'] = [sys.argv[2]]
	elif ((len(sys.argv)>1) and (len(sys.argv)==4)):
		FoodPersonData['days'] = [sys.argv[1]]
		FoodPersonData['occasion'] = [sys.argv[2]]
		FoodPersonData['attendance'] = [int(sys.argv[3])]	
	
	
	foodperson =  fp.predictFood(model,FoodPersonData)
	print (int(foodperson))	
	
main()
