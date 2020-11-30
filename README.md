# ToyAndroid

 1:发布Lib/module到Maven 命令

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

 2：项目打渠道包
 
        classpath 'com.android.tools.build:gradle:3.6.3'
        distributionUrl=https\://services.gradle.org/distributions/gradle-5.6.4-all.zip

        注：com.android.tools.build:4.0.x 之后walle 打渠道包失效
           必须使用4.0.x 之前版本
           // https://github.com/Meituan-Dianping/walle/issues/331

       ./gradlew  clean --info  --warning-mode all
       ./gradlew clean assembleReleaseChannels


