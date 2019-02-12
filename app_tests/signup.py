import requests
from requests.auth import HTTPBasicAuth

headers = {'Content-Type': 'application/json'}
payload = '{"name": "xyz", "password": "banana"}'

r = requests.post("http://localhost:8080/sign-up", data=payload, headers=headers)
print(r)
uuid = r.json()['uuid']


payload = '{"name":"xyz", "type":"DETECTIVE"}'
r = requests.post("http://localhost:8080/player/" + uuid + "/character", data=payload, headers=headers,
                  auth=HTTPBasicAuth('xyz', 'banana'))
print(r)
print(r.json())
