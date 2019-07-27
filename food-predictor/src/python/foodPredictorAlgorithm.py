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
from pickletools import uint8
inst_log = Logger.Logger()
fp_logger = inst_log.setLoggerConfig()

homeDir = path.abspath(path.join(__file__,"../../../../"))
dataPath = path.abspath(path.join(__file__,"../../"))
datafile = dataPath + "/data//fwdata.csv"
uiPath = dataPath + "/main/webapp/images/"

from pathlib import Path
home = str(Path.home())
# print(home)


FoodPerson_pred = pd.DataFrame()

class foodPred:
	
	def __init__(self):
		fp_logger.debug("food predictor class instantiated")
	
	def loadDataset(self,filename, tg, split):
		
		fp_logger.info('loading dataset and preparing training and test set')
		df= pd.read_csv(filename,header=0)
		target = df[tg]
		df = df.drop(tg, axis=1)
		training_features = df.drop('Date', axis=1)
		training_features = pd.get_dummies(training_features, columns=['days'],prefix = ['days'])
		training_features = pd.get_dummies(training_features, columns=['occasion'],prefix = ['occasion'])
		training_features = pd.get_dummies(training_features, columns=['category'],prefix = ['category'])

		global FoodPerson_pred
		FoodPerson_pred = training_features.copy()
		col_count = FoodPerson_pred.shape[0]
		FoodPerson_pred = FoodPerson_pred[:-col_count]
		FoodPerson_pred[0]=[0]
		del FoodPerson_pred[0]
		FoodPerson_pred = FoodPerson_pred.replace(np.nan, 0)

		return train_test_split(training_features, target, train_size=split)
	
	def predictFood(self,model,FoodPersonData):
		fp_logger.info('predicting the food person data using the prepared model')
		FoodPerson = pd.DataFrame(FoodPersonData)

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
		plt.savefig(uiPath + '//predictedFoodPerson.png', dpi=100)
		plt.savefig(home + '//predictedFoodPerson.png', dpi=100)
		
		#plt.show()
	
	def createModel(self):
		# prepare data
		target = 'attended'
		
	
		train_size = 0.7
		train_x, test_x, train_y, test_y = self.loadDataset(datafile, target,train_size)
	
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
		accuracy = model.score(test_x,test_y)
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

	FoodPerson_pred1 = {}
	FoodPerson_pred1=FoodPerson_pred.to_dict('list')
	FoodPerson_pred1['attendance'] = [int(sys.argv[4])]
	
# 	print (sys.argv[1])
# 	print (sys.argv[2])
# 	print (sys.argv[3])
# 	print (sys.argv[4])
	
	temp = 'days_'+str(sys.argv[1])
	FoodPerson_pred1[temp] = [1.0]
	
	temp = 'occasion_'+str(sys.argv[2])
	FoodPerson_pred1[temp] = [1.0]
	
	temp = 'category_'+str(sys.argv[3])
	FoodPerson_pred1[temp] = [1.0]
	

	
# 	mylist = ['days','occasion']
# 	
# 	for i in mylist:
# 		temp = i + "_"+ str(sys.argv[1])
# 		FoodPerson_pred1[temp] = [1.0]
	
	
# 	print (FoodPerson_pred1)

	foodperson =  fp.predictFood(model,FoodPerson_pred1)
	print (int(foodperson))	
 	
main()
