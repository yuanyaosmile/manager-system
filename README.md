# manager-system


# 1.get resource 
```
curl --location --request GET 'localhost:10086/user/resource A' \
--header 'RoleInfo: eyJ1c2VySWQiOiIxMjM0NTYiLCJhY2NvdW50TmFtZSI6Inh4eHgiLCJyb2xlIjoiQURNSU4ifQ'

```

# 2. add user
```
curl --location --request POST 'localhost:10086/admin/addUser' \
--header 'RoleInfo: eyJ1c2VySWQiOiI1Njc4IiwiYWNjb3VudE5hbWUiOiJ4eHh4Iiwicm9sZSI6IkFETUlOIn0' \
--header 'Content-Type: application/json' \
--data-raw '{
"userId":"123",
"endpoint": [
"resource A",
"resource B",
"resource C"
]
}'
```