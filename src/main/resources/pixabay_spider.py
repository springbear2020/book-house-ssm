from datetime import date
from msilib.schema import Condition
from unicodedata import category
import requests
import json
import sys
import pymysql


# Input the filter condition of the page
# condition = input("condition:")
# Input the total pages you want to get
# page = input('page(20 pictures per page):')

# Get the parameters from the command panel
condition = sys.argv[1]
page = sys.argv[2]

print('condition:' + condition)
print('page:' + page)

req_params = {
    'key': '26183514-10870193e7b68335958cb6abb',
    'q': condition,
    'lang': 'en',
    'orientation': 'horizontal',
    'page': -1,
}

# Set the database info 
db = pymysql.connect(host='localhost', user="admin", passwd="admin", port=3306, db='panda', charset='utf8')
cursor = db.cursor()

saveCount = 0
for i in range(0, int(page)):
    # Parse the specified pages response content into json object
    req_params['page'] = i + 1
    request = requests.get('https://pixabay.com/api/', params=req_params, headers={'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36'})
    decode_json = json.loads(request.text)

    # Save the relative info into database
    for picture in decode_json['hits']:
        saveCount = saveCount + 1
        print("The " + str(saveCount) + " pictures is saving to the database...")
        sql = """INSERT INTO `t_pixabay`(`condition`,`tags`,`views`,`downloads`,`collections`,`likes`,`comments`,`add_time`,`url`) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s);"""
        cursor.execute(sql, (condition, picture['tags'], picture['views'],picture['downloads'], picture['collections'], picture['likes'], picture['comments'],  date.today(),picture['largeImageURL']))
        db.commit()
cursor.close()
db.close()