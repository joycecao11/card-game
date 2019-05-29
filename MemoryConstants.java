public interface MemoryConstants{
	int row = 2;
	int column = 3;
   
	int PORT = 100;
   
	int DISPLAY = 0;
	int QUIT = 3;
	int RESTART = 4;
	
	int COVER = 1;
	int REVEAL = 2;
	int DONE = 5;
	int SHOWCARD = 6;
	int cannotChooseMoreThanTwoCards = 7;
	int itIsNotYourTurn = 8;
	int PAIRS = 9;
	int WINNER = 10;
	int WAIT = 11;
	int ISWINNER = 12;
	int ISNOTWINNER = 13;

   
	default String cmdToString(int cmd)
	{
		String cmdString;
		switch (cmd)
		{
			case cannotChooseMoreThanTwoCards:
				cmdString = "cannot choose more than two cards";
				break;
			case itIsNotYourTurn: 
				cmdString = "it is not your turn";
				break;
			case QUIT: 
				cmdString = "QUIT";
				break;
			case DONE: 
				cmdString = "DONE";
				break;
			default:
				cmdString = "UNRECOGNIZABLE COMMAND";
		}  // switch
		return cmdString;
	} // cmdToString
}
