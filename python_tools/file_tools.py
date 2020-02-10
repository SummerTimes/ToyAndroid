#!/usr/bin/python
# -*- coding: utf-8 -*-


import re
import os
import tempfile


def replace_or_append_text(file_name, from_regex, to_str, append_on_not_exists=True, comment_mark='#'):
    file = tempfile.TemporaryFile()         #创建临时文件

    if os.path.exists(file_name):
        r_open = open(file_name,'r')
        pattern = re.compile(r'' + from_regex)
        found = None
        for line in r_open: #读取原文件
            if pattern.search(line) and not line.strip().startswith(comment_mark):
                found = True
                line = re.sub(from_regex, to_str, line)
            file.write(line)   #写入临时文件
        if not found and append_on_not_exists:
            file.write('\n' + to_str)
        r_open.close()
        file.seek(0)

        content = file.read()  #读取临时文件中的所有内容

        if os.path.exists(file_name):
            os.remove(file_name)

        w_open = open(file_name,'w')
        w_open.write(content)   #将临时文件中的内容写入原文件
        w_open.close()

        file.close()  #关闭临时文件，同时也会自动删掉临时文件
    else:
        print "file %s not found" % file_name
