## Branches:
* main
* feature/crud-customer
* feature/pagination
* feature/upload

## Request
                    
| HTTP  | Route | Function name
| ------------- | -------------| ------------ |
| GET |/customer |listAll
| GET |/customer/page/{page}| listAll 
| GET |/customer/{id}| getById 
| POST |/customer| create 
| PUT| /customer/{id}| update 
| DELETE| /customer/{id}| delete 
| POST| /customer/uploads| upload 
|GET |/uploads/img/{namePhoto:.+} |showPhoto 

src/main/java
