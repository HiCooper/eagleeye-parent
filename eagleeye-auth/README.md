# 用户管理+授权服务中心

## 1.用户管理

- 系统用户管理
- 角色管理
- 权限管理
- 角色权限管理
- 用户角色权限管理
- oauth2 授权客户端管理（inner service）

## 2.授权服务

- 用户登录
- 用户注册
- oauth2客户端认证 `client_credentials`
````
# request
curl --location --request POST 'http://localhost:8033/oauth/token' \
--form 'grant_type=client_credentials' \
--form 'scope=web-app' \
--form 'client_id=internal' \
--form 'client_secret=internal'

# response
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib3JkZXIiXSwic2NvcGUiOlsid2ViLWFwcCJdLCJleHAiOjE2MTU2OTU5NjEsImlhdCI6MTYxNTY5MjM2MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9JTk5FUl9TRVJWSUNFIl0sImp0aSI6ImEyOTY4OGE3LTUxZDQtNDJmOS1iMjU2LWE1OWFhNTczZDMyZSIsImNsaWVudF9pZCI6ImludGVybmFsIn0.E89AJUiKDwW6urkle6gHK4RrTe4d0SqPoVjFWWq33xZ7zAi-A7-vO2hSmRJ3kKUoR2YhVqUKfGFs6FjIb1qGyuzpkQrj4eYQGrVHjrXbczat96sLfUd1ng0ym-WMA6Jaqpu_IOWmorTtW51Zi6c-1FOaYdvqwCcZdY5zeSxEKUiu1hmm_xvEHv3gj2StYwPKRMfNlLXV7oRycBd6WAYueYEP3wk0aJ2stpp_UUr5A739BVDbL6A7bNA1H1iQgGHFckgd5hqVbxQldbX3yEY8HlhjO5_k2GjC4_492E4FIaP6gj630vI1kYc196fYuQol253witATJQ95mF2sUDMmEQ",
  "token_type": "bearer",
  "expires_in": 3599,
  "scope": "web-app",
  "iat": 1615692361,
  "jti": "a29688a7-51d4-42f9-b256-a59aa573d32e"
}
````


## 3.内部调用接口
> 需通过oauth2验证 
- 根据用户ID 获取该用户的角色列表
- 根据 SDK 密钥key 查找用户
