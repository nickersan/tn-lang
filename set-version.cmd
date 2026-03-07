@echo off

if "%1"=="" (
    echo No version provided.
    exit /b 1
)

git commit --allow-empty -m "chore: release %1" -m "Release-As: %1"
