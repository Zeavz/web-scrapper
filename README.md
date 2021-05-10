Application is deployed at https://jenis-web-scrapper.herokuapp.com

the web scrapper is wrapped as a REST service for ease of testing

curl to trigger the webcrawler:

`curl --request GET \
  --url 'https://jenis-web-scrapper.herokuapp.com/scrapping/start?url=https%3A%2F%2Fwww.sedna.com%2F'`
  
  The output will be as follows:
  
  {[\
  &nbsp;&nbsp;"routes": these are the main routes on the domain of this webpage {\
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"assets": This is a list of all assets found on the current route,\
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"outboundLinks": These are the links that could be navigated to from the current route\
  &nbsp;&nbsp;}\
  ]}
  
  
  To build the project run
  `./mvnw clean install`
  
  To run the project run
  `./mvnw spring:boot run`


