# JVM bytecode assembler server
Assemble JVM bytecode via REST endpoint

## Example

```bash
$ mvn package

$ jvm-assembler-server/target/appassembler/bin/jvmasm --port 8080 &

$ cat examples/Helloworld.json
```
```json
{
    "instructions": [
        {
            "flags": 1,
            "type": "CreateClass"
        },
        {
            "parent": "java/lang/Object",
            "interfaces": [],
            "name": "Helloworld",
            "version": 52,
            "sig": null,
            "type": "ClassCodeStart",
            "acc": 1
        },
        {
            "type": "SourceInfo",
            "name": "Helloworld.idr"
        },
        {
            "excs": null,
            "name": "<init>",
            "sig": null,
            "type": "CreateMethod",
            "desc": "()V",
            "acc": 1
        },
        {
            "type": "MethodCodeStart"
        },
        {
            "type": "Aload",
            "index": 0
        },
        {
            "cname": "java/lang/Object",
            "isIntf": false,
            "mname": "<init>",
            "type": "InvokeMethod",
            "desc": "()V",
            "invType": 183
        },
        {
            "type": "Return"
        },
        {
            "nstack": -1,
            "type": "MaxStackAndLocal",
            "nlocal": -1
        },
        {
            "type": "MethodCodeEnd"
        },
        {
            "excs": null,
            "name": "main",
            "sig": null,
            "type": "CreateMethod",
            "desc": "([Ljava/lang/String;)V",
            "acc": 9
        },
        {
            "type": "MethodCodeStart"
        },
        {
            "cname": "java/lang/System",
            "ftype": 178,
            "type": "Field",
            "desc": "Ljava/io/PrintStream;",
            "fname": "out"
        },
        {
            "constType": "StringConst",
            "type": "Ldc",
            "val": "Hello world!"
        },
        {
            "cname": "java/io/PrintStream",
            "isIntf": false,
            "mname": "println",
            "type": "InvokeMethod",
            "desc": "(Ljava/lang/Object;)V",
            "invType": 182
        },
        {
            "type": "Return"
        },
        {
            "nstack": -1,
            "type": "MaxStackAndLocal",
            "nlocal": -1
        },
        {
            "type": "MethodCodeEnd"
        },
        {
            "type": "ClassCodeEnd",
            "out": "Helloworld.class"
        }
   ]
}
```

```bash
$ curl -H "Content-Type: application/json" -X POST -d @examples/Helloworld.json http://localhost:8080/assembler/assemble
{"message":"","success":true}

$ java Helloworld
Hello world!
```
