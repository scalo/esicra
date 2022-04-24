
#
# usage: _read_rsp <in:question>
#                  <out:answer>
# return values:
#  0 on succesful completion
#  1 otherwise
#
__read_rsp()
{
	if [ $# != 2 ]
	then
		return 1;
	fi

	_question=$1
	_answer=""
	while [ "$_answer" = "" ]
	do
		echo -e "$_question\c"
		read _answer
	done

	eval "$2=$_answer"
	return 0
}

#
# usage: _read_yn_rsp <in:question>
#                     <out:answer>
# return values:
#  0 on succesful completion
#  1 otherwise
#
__read_yn_rsp()
{
	if [ $# != 2 ]
	then
		return 1;
	fi

	_question=$1
	_answer=""
	_yn_answer=""
	while [ "$_yn_answer" != "y" -a "$_yn_answer" != "n" ]
	do
		__read_rsp "$_question" _answer
		_yn_answer=`echo $_answer | /bin/sed 's/Y/y/g' | /bin/sed 's/N/n/g'`

		if [ "$_yn_answer" != "y" -a "$_yn_answer" != "n" ]; then
			echo
			echo "Check response correctness!!!"
			echo
		fi
	done

	eval "$2=$_yn_answer"
	return 0
}
