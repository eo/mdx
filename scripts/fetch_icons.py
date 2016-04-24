#!/usr/bin/env python3

import json
import os
from collections import OrderedDict
from urllib import request

FILE_DIR = os.path.dirname(__file__)

API_DIR = '../api'
API_VERSION = 'v1'
JSON_FILE_NAME = 'material-icon-groups.json'

ICONS_DATA_URL = 'https://design.google.com/icons/data/grid.json'

CACHE_DIR = 'cache/'
CACHE_FILE_NAME = 'icons_data.json'

JSON_FILE_PATH = os.path.join(FILE_DIR, API_DIR, API_VERSION, JSON_FILE_NAME)
CACHE_FILE_PATH = os.path.join(FILE_DIR, CACHE_DIR, CACHE_FILE_NAME)

def download_icons_data():
    request.urlretrieve(ICONS_DATA_URL, CACHE_FILE_PATH)

def read_icons_data():
    with open(CACHE_FILE_PATH) as cache_file:
        return json.load(cache_file)

def fetch_icons_data():
    try:
        download_icons_data()
    except:
        print('Error downloading from URL: ' + ICONS_DATA_URL)
        print('Reading from cache file: ' + CACHE_FILE_PATH)
    finally:
        return read_icons_data()

def normalize_icons_data(icons_data):
    icon_groups = []
    group_id_map = {}

    for group_dict in icons_data['groups']:
        group_data = group_dict['data']

        icon_group = OrderedDict()
        icon_group['name'] = group_data['name']
        icon_group['icons'] = []

        group_id_map[group_data['id']] = icon_group
        icon_groups.append(icon_group)

    for icon_dict in icons_data['icons']:
        icon = OrderedDict()
        icon['name'] = icon_dict['name']
        icon['keywords'] = ' '.join(icon_dict['keywords'])
        icon['file'] = icon_dict['id']
        icon['codepoint'] = icon_dict['codepoint']

        icon_group = group_id_map[icon_dict['group_id']]
        icon_group['icons'].append(icon)

    return icon_groups

def save_json(json_data, file_path):
    with open(file_path, 'w') as json_file:
        json.dump(json_data, json_file, indent=2)


if __name__ == '__main__':
    icons_data = fetch_icons_data()
    normalized_data = normalize_icons_data(icons_data)
    save_json(normalized_data, JSON_FILE_PATH)
