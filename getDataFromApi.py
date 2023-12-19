import json
import subprocess
import sys
import os

a = sys.argv[1]

dir = os.path.dirname(os.path.abspath(__file__))
url_file_path = os.path.join(dir, 'apiurl.txt')

with open(url_file_path, 'r') as file:
    url = file.read().strip()

curl_command = f'curl -s -X POST -H "Content-Type: application/json" --data \'{{ "data": ["{a}"] }}\' {url}'

result = subprocess.run(curl_command, shell=True, capture_output=True, text=True)

if result.returncode == 0:
    json_response = json.loads(result.stdout)
    tone = json_response.get('data', [{}])[0].get('confidences', [{}])[0].get('label', None)

    if tone is not None:
        print(tone)
    else:
        print("neutral")
else:
    print("neutral")

