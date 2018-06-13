@ECHO OFF

ECHO Please note that Cx Server on Windows is for convenient development and evaluation.
ECHO For running an productive Cx Server, please use a Linux System.

IF %~1==backup GOTO NOT_SUPPORTED
IF %~1==restore GOTO NOT_SUPPORTED
IF %~1==update (
    IF "%~2"=="image" GOTO NOT_SUPPORTED
)

IF "%DEVELOPER_MODE%"=="" docker pull s4sdk/cxserver-companion
docker run --rm -it --workdir /cx-server/mount --volume //var/run/docker.sock:/var/run/docker.sock --mount source="%CD%",target=/cx-server/mount,type=bind --env DEVELOPER_MODE --env http_proxy --env https_proxy --env no_proxy --env host_os=windows --env cx_server_path="%CD%" s4sdk/cxserver-companion /cx-server/cx-server-companion.sh %~1 %~2
GOTO END

:NOT_SUPPORTED
ECHO "backup", "restore" and "update image" are currently not supported on Windows.

:END
