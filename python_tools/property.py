#!/usr/bin/python
# -*- coding: utf-8 -*-


import re
import os
import tempfile
import file_tools

class Properties:

    def __init__(self, file_name):
        self.file_name = file_name
        self.properties = {}
        try:
            fopen = open(self.file_name, 'r')
            for line in fopen:
                line = line.strip()
                if line.find('=') > 0 and not line.startswith('#'):
                    strs = line.split('=')
                    self.properties[strs[0].strip()] = strs[1].strip()
        except Exception, e:
            raise e
        else:
            fopen.close()

    def has_key(self, key):
        return self.properties.has_key(key)

    def get(self, key, default_value=''):
        if self.properties.has_key(key):
            return self.properties[key]
        return default_value

    def put(self, key, value, append_on_not_exist=True):
        self.properties[key] = value
        file_tools.replace_or_append_text(self.file_name, key + '=.*', key + '=' + value, append_on_not_exist, '#')

    def keys(self):
        list = []
        for key in self.properties:
            list.append(key)
        return list

def parse(file_name):
    return Properties(file_name)

