# MailPortServer

### 功能说明

通过本程序监听计算机上的某一端口，接受其他应用程序传递过来的特定格式的json格式字符串字节流，来操控计算机，通过用户个人的qq邮箱，向指定的邮箱发送文本邮件。

### 安装说明

#### 1. 安装jdk11及以上

具体请查询搜索引擎。

#### 2. 下载jar文件

下载Release中的MailPortServer-\*.\*.tar.gz，并解压得到MailPortServer-\*.\*.jar及config.json

#### 3. 配置config.json

config.json文件中，存储了自己邮箱且**目前仅支持qq邮箱**，及qq邮箱的授权码（见[wiki](https://github.com/leonardodalinky/MailPortServer/wiki/%E8%8E%B7%E5%8F%96QQ%E9%82%AE%E7%AE%B1%E6%A0%A1%E9%AA%8C%E7%A0%81)）。json格式为：

```json
{
	"userMail":"someone@qq.com",
	"authCode":"asdfsdljsidfiedw"
}
```

#### 4. 运行

在MailPortServer-\*.\*.jar的同目录下，执行命令

```powershell
java -jar ./MailPortServer-*.*.jar 5001
```

其中*5001*为自己选择需要监听的端口。

### 使用说明

#### 1. 说明

运行程序后，现在只需向服务端监听的端口，通过socket传送**UTF-8**编码下的特定格式的json字符串字节。json文件的格式为：

```json
{
	"ToHost":"someone@mail.com",
	"Subject":"title",
	"Message":"something"
}
```

参数说明：

* ToHost: 接收方的邮箱地址
* Subject: 邮件标题
* Message: 邮件内容

#### 2. python示例

```python
# -*- coding: UTF-8 -*-

import socket  # 导入 socket 模块

s = socket.socket()  # 创建 socket 对象
host = socket.gethostname()  # 获取本地主机名
port = 5001  # 设置端口号

s.connect((host, port))
content = '{"ToHost":"toemail@qq.com", "Message":"succeed<br>yeah", "Subject":"testing"}'
s.send(content.encode('utf-8'))
s.close()
```

运行上述python脚本，即可通过自己的邮箱，向toemail@qq.com发送标题为"testing"且内容为"succeed\<br\>yeah"的邮件。

注意，邮箱文本支持html格式，建议以\<br\>代替'\n'
