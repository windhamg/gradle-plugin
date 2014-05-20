package org.netkernel.gradle.util

import spock.lang.Specification

class FileSystemHelperSpec extends Specification {

    FileSystemHelper fileSystemHelper = new FileSystemHelper()

    def 'dir exists #directory'() {
        setup:
        File file = new File(FileSystemHelperSpec.getResource('/test').file, path)

        when:
        boolean result = fileSystemHelper.exists(file.absolutePath)

        then:
        result == expectedResult

        where:
        path                             | expectedResult
        '/gradleHomeDirectory'           | true
        '/gradleHomeDirectory/netkernel' | true
        '/doesntexist'                   | false
    }

    def 'gets gradle home dir'() {
        expect:
        fileSystemHelper.gradleHomeDir().endsWith('/.gradle')
    }

    def 'creates directories'() {
        setup:
        File dirName = new File(FileSystemHelperSpec.getResource('/test').file, directory)

        when:
        boolean result = fileSystemHelper.createDirectory(dirName.absolutePath)

        then:
        result == expectedResult

        where:
        directory            | expectedResult
        'workdir'            | true
        'workdir/newfolder'  | true
        'workdir/readme.txt' | false
    }

    def 'retrieves directory in gradle home directory'() {
        setup:
        File gradleHome = new File(FileSystemHelperSpec.getResource('/test/gradleHomeDirectory').file)
        fileSystemHelper.@_gradleHome = gradleHome.absolutePath

        expect:
        fileSystemHelper.dirInGradleHomeDirectory('netkernel') == new File(gradleHome, 'netkernel').absolutePath
    }

    def 'directory exists in gradle home directory'() {
        setup:
        File gradleHome = new File(FileSystemHelperSpec.getResource('/test/gradleHomeDirectory').file)
        fileSystemHelper.@_gradleHome = gradleHome.absolutePath

        expect:
        fileSystemHelper.dirExistsInGradleHomeDirectory('netkernel')
    }

    def 'creates directory in gradle home directory'() {
        setup:
        File gradleHome = new File(FileSystemHelperSpec.getResource('/test/gradleHomeDirectory').file)
        fileSystemHelper.@_gradleHome = gradleHome.absolutePath

        when:
        fileSystemHelper.createDirInGradleHomeDirectory('hello')

        then:
        gradleHome.listFiles().find { it.name == 'hello' }
    }

    def 'determines if file exists'() {

    }

}
