Feature: Check UI Bank Module

  Scenario: check create New Account and get All Account
	Given enable logs
	When login in to Ui Bank
	And create New Account
	And get All Account
	
	