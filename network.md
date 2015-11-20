##### 注册
方法：post
表单字段：data
URL:/register
格式：
```
{
    "phone": "12345678901",
    "username": "Tom",
    "password": "123456"
}
```
响应(注册成功)：
```
{
    "state": "success",
    "reason": ""
}
```
响应(注册失败)：
```
{
    "state": "fail",
    "reason": "手机号已经被注册"
}
```
_ _ _
##### 登录
方法：post
表单字段：data
URL:/login
格式：
```
{
    "phone": "12345678901",
    "password": "123456"
}
```
响应(登录失败)：
```
{
    "state": "fail",
    "reason": "密码错误"
}
```
响应（登录成功，并取出该用户所有数据）
```
{
    "state": "success",
    "user": {
        "detail": "未填写",
        "phone": "12345678901",
        "username": "xiaoming",
        "timeTables": [
            {
                "alarm": true,
                "day": 13,
                "hour": 13,
                "id": 3,
                "minute": 25,
                "month": 11,
                "star": false,
                "text": "123",
                "title": "123",
                "year": 2015
            },
            {
                "alarm": false,
                "day": 13,
                "hour": 22,
                "id": 4,
                "minute": 3,
                "month": 11,
                "star": false,
                "text": "secondtext",
                "title": "second",
                "year": 2015
            }
        ]
    }
}
```


_ _ _

##### 增加/更改timetable记录
方法：put
表单字段：data
URL:/add
/update
格式：
```
{
    "alarm": false,
    "day": 13,
    "hour": 22,
    "id": 4,
    "minute": 3,
    "month": 11,
    "star": false,
    "text": "secondtext",
    "title": "second",
    "year": 2015
}
```
响应(增加/更新成功)：
```
{
    "state": "success",
    "reason": ""
}
```
响应(增加/更新失败)：
```
{
    "state": "fail",
    "reason": "手机号已经被注册"
}
```

_ _ _
##### 删除timetable记录
方法：delete
表单字段：data
URL:/delete
格式：
```
{
    "id": 35
}
```
响应(删除成功)：
```
{
    "state": "success",
    "reason": ""
}
```
响应(删除失败)：
```
{
    "state": "fail",
    "reason": "手机号已经被注册"
}
```

_ _ _
##### 轮询获取服务器同步
方法：get
URL:/get
响应（增删改）：
```
{
    "add": [
        {
            "alarm": true,
            "day": 13,
            "hour": 13,
            "id": 3,
            "minute": 25,
            "month": 11,
            "star": false,
            "text": "123",
            "title": "123",
            "year": 2015
        },
        {
            "alarm": false,
            "day": 13,
            "hour": 22,
            "id": 4,
            "minute": 3,
            "month": 11,
            "star": false,
            "text": "secondtext",
            "title": "second",
            "year": 2015
        }
    ],
    "update": [
        {
            "alarm": true,
            "day": 13,
            "hour": 13,
            "id": 9,
            "minute": 25,
            "month": 11,
            "star": false,
            "text": "123",
            "title": "123",
            "year": 2015
        }
    ],
    "delete": [
        {
            "id": 5
        },
        {
            "id": 23
        }
    ]
}
```

