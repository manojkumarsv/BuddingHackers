# -*- coding: utf-8 -*-
"""
Created on Thu Jul 25 13:19:58 2019

@author: Hack5CHETeam4
"""

import logging
import os
import os.path as path
from imp import reload
reload(logging)

homeDir = path.abspath(path.join(__file__,"../../../../"))
logfile = homeDir + "\log\\foodPredictor.log"

class Logger:
	def setLoggerConfig(self):
#		cwd = os.getcwd()
#		logfile = cwd+"\\foodPredictor.log"
		
		# Logger configuration:
		log_level = logging.DEBUG 
		log_format = '%(asctime)s %(levelname)s:%(message)s'
		
		# Testing logger
		logger = logging.root
		logger.basicConfig = logging.basicConfig(format=log_format, filename=logfile, level=log_level)
		
		return logger
	
