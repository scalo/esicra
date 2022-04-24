#!/bin/sh


for file in `ls ./*.XML`
do
	cat $file | /bin/sed s/PNLGNN77H8763HJZ/RSSMRA70C20A794O/g > ${file}.tmp
	/bin/mv -f ${file}.tmp $file
	cat $file | /bin/sed s/BCCNNA70M41A794K/BNCNNA70D60A794N/g > ${file}.tmp
	/bin/mv -f ${file}.tmp $file	
	cat $file | /bin/sed s/PNLMRC98H8763HJZ/RSSNDR00R08A794I/g > ${file}.tmp
	/bin/mv -f ${file}.tmp $file
	cat $file | /bin/sed s/VRDCRN52C70H558I/RSSMHL01R08A794M/g > ${file}.tmp
	/bin/mv -f ${file}.tmp $file
done
