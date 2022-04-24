

#
# usage: _get_create_user_input <out:username>
#                               <out:passowrd>
# return values:
#  0 on succesful completion
#  1 otherwise
#
__get_create_user_input()
{
	if [ $# != 2 ]
	then
		return 1;
	fi

	_username=""
	_password=""

	_yn_rsp=""
	while [ "$_yn_rsp" != "y" ]
	do
		echo
		echo
		_question="Insert New Username [CTRL+C abort]:> "
		__read_rsp "$_question" _username

		echo
		echo
		_question="Insert New User Password [CTRL+C abort]:> "
		__read_rsp "$_question" _password


		echo
		echo
		echo "Following data will be used:"
		echo
		echo
		echo "    Username  : $_username"
		echo "    Password  : $_password"
		echo
		echo
		_question="Do you agree [y/n] [CTRL+C abort]? > "
		__read_yn_rsp "$_question" _yn_rsp
	done

	eval "$1=$_username"
	eval "$2=$_password"
	return 0
}

#
# usage: _get_create_db_input <out:dbname>
#                             <out:dbowner>
# return values:
#  0 on succesful completion
#  1 otherwise
#
__get_create_db_input()
{
	if [ $# != 2 ]
	then
		return 1;
	fi

	_dbname=""
	_dbowner=""

	_yn_rsp=""
	while [ "$_yn_rsp" != "y" ]
	do
		echo
		echo
		_question="Insert DB Name [CTRL+C abort]:> "
		__read_rsp "$_question" _dbname

		echo
		echo
		_question="Insert DB Owner [CTRL+C abort]:> "
		__read_rsp "$_question" _dbowner


		echo
		echo
		echo "Following data will be used:"
		echo
		echo
		echo "    DB Name   : $_dbname"
		echo "    DB Owner  : $_dbowner"
		echo
		echo
		_question="Do you agree [y/n] [CTRL+C abort]? > "
		__read_yn_rsp "$_question" _yn_rsp
	done

	eval "$1=$_dbname"
	eval "$2=$_dbowner"
	return 0
}


#
# usage: _get_create_db_input <out:username>
#                             <out:dbname>
#                             <out:dbschema>
# return values:
#  0 on succesful completion
#  1 otherwise
#
__get_create_dbschema_input()
{
	if [ $# != 3 ]
	then
		return 1;
	fi

	_username=""
	_dbname=""
	_dbschema=""

	_yn_rsp=""
	while [ "$_yn_rsp" != "y" ]
	do
		echo
		echo
		_question="Insert User Name to Connect with [CTRL+C abort]:> "
		__read_rsp "$_question" _username

		echo
		echo
		_question="Insert DB Name to connect to [CTRL+C abort]:> "
		__read_rsp "$_question" _dbname

		echo
		echo
		_question="Insert DB Schema name [CTRL+C abort]:> "
		__read_rsp "$_question" _dbschema


		echo
		echo
		echo "Following data will be used:"
		echo
		echo
		echo "    UserName  : $_username"
		echo "    DB Name   : $_dbname"
		echo "    DB Schema : $_dbschema"
		echo
		echo
		_question="Do you agree [y/n] [CTRL+C abort]? > "
		__read_yn_rsp "$_question" _yn_rsp
	done

	eval "$1=$_username"
	eval "$2=$_dbname"
	eval "$3=$_dbschema"
	return 0
}


#
# usage: _get_loading_input <out:username>
#                           <out:dbname>
# return values:
#  0 on succesful completion
#  1 otherwise
#
__get_loading_input()
{
	if [ $# != 3 ]
	then
		return 1;
	fi

	_username=""
	_dbname=""
	_dbschema=""

	_yn_rsp=""
	while [ "$_yn_rsp" != "y" ]
	do
		echo
		echo
		_question="Insert User Name to connect with [CTRL+C abort]:> "
		__read_rsp "$_question" _username

		echo
		echo
		_question="Insert DB Name to connect to [CTRL+C abort]:> "
		__read_rsp "$_question" _dbname

		echo
		echo
		_question="Insert DB Schema to load into [CTRL+C abort]:> "
		__read_rsp "$_question" _dbschema


		echo
		echo
		echo "Following data will be used:"
		echo
		echo
		echo "    UserName  : $_username"
		echo "    DB Name   : $_dbname"
		echo "    DB Schema : $_dbschema"
		echo
		echo
		_question="Do you agree [y/n] [CTRL+C abort]? > "
		__read_yn_rsp "$_question" _yn_rsp
	done

	eval "$1=$_username"
	eval "$2=$_dbname"
	eval "$3=$_dbschema"
	return 0
}

