#!/bin/bash
# Setup project including name and packageName
# Tested on Pop!_OS 20.04

clear
echo
echo -e "Remove Git? (y/n)"
echo -e "(create your own project: yes)"
echo -e "(improve Skeleton: no)"
read -r removeGit

if [ "$removeGit" != "${removeGit#[Yy]}" ]; then
    rm -rf .git
    rm -rf .idea
    
    echo
    echo -e "This replaces the packageName in all files (recursively)"
    read -r -p "Enter new packageName: " newPackageName

    escapedNewPackageName=$(printf '%s\n' "$newPackageName" | sed -e 's/[\/&]/\\&/g')

    ( shopt -s globstar dotglob;
        for file in **; do
            if [[ -f $file ]] && [[ -w $file ]]; then
                sed -i -- "s/de.datlag.skeleton/$escapedNewPackageName/g" "$file"
            fi
        done
    )
    
    defaultNewName="${escapedNewPackageName##*.}"
    defaultNewName="$(tr '[:lower:]' '[:upper:]' <<< ${defaultNewName:0:1})${defaultNewName:1}"
    read -r -p "Enter new ApplicationName (default: $defaultNewName): " newAppName

    escapedNewAppName=$(printf '%s\n' "$newAppName" | sed -e 's/[\/&]/\\&/g')
    currentDir="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
    parentDir="$(dirname "$currentDir")"
    currentProjectName="${currentDir##*/}"
    
    if [[ -z "${escapedNewAppName// }" ]]; then
        actualNewName=$defaultNewName
    else
        actualNewName=$escapedNewAppName
    fi

    sed -i -- "s/$currentProjectName/$actualNewName/g" ./settings.gradle.kts
    cd "$parentDir"
    mv "$currentDir" "$parentDir/$actualNewName"
    cd "$parentDir/$actualNewName"
    echo
    echo "Success"
    echo "ToDo: Refactor packageName in Android Studio"
    echo
fi
