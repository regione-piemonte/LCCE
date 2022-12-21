#!/bin/bash


BASEDIR=$(readlink -e $(dirname $(readlink -e "$0"))/..)

main() {
   mkdir -p ${BASEDIR}/log/

   echo "Starting in $BASEDIR..."

   java -cp "${BASEDIR}/lib/*:${BASEDIR}/config" it.csi.solconfig.batchabilitazconfig.Main >${BASEDIR}/log/STDOUT_batchabilitazconfig.log 2>&1 &

}


main $@
