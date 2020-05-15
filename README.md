# ToyAndroid

发布Lib/module到Maven 命令

    print '     +--------------------------------------------------------------------------------------------------------'
    print '     |    发布module到maven服务器                                                                               '
    print '     |    usage:                                                                                              '
    print '     |            python deploy.py [-c] [-l] [-r] [-a] module_name                                            '
    print '     |                -c： 已废弃，可选，只发布当前指定的module（默认只就是这个）                                       '
    print '     |                -d： 可选，同时deploy依赖本module的其它module                                                '
    print '     |                -l： mac可选，打印发布信息的log日志                                                          '
    print '     |                -r： 可选，发布成release版本，默认为snapshot版本                                              '
    print '     |                -a： 可选，发布artifactory_veresion.properties中配置的所有module                             '
    print '     |       module_name： 无-a参数时必填，当前发布的module名称                                                     '
    print '     +---------------------------------------------------------------------------------------------------------'


