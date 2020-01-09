export default {
  langName: 'English',
  menu: {
    play: 'Play',
    settings: 'Settings',
    rules: 'Rules'
  },
  gamesPanel: {
    name: 'Name',
    players: 'Players',
    bots: 'Bots',
    state: 'State',
    address: 'Address',
    title: 'Join a game',
    username: 'as %s',
    filter: {
      title: 'Filters',
      publicGamesOnly: 'Public games',
      realPlayersOnly: 'Real players only'
    },
    serverList: {
      name: 'Name',
      realPlayers: 'Real players',
      virtualPlayers: 'Bots',
      status: 'Status',
      address: 'Address'
    },
    alreadyConnected: 'You are connected as %s',
    inputUsername: 'Please specify your username',
    useSameUsername: 'Or update your username below',
    invalidSyntax: 'Your username must not contain %s',
    joinServer: 'Would you like to join %s ?',
    waitingStart: 'Connecting to the game...',
    joinReject: 'Connection to the game rejected',
    joinError: 'Unable to connect to the game'
  },
  game: {
    settings: {
      changeLang: 'Change language',
      soundEffects: 'Sound effects',
      backToGame: 'Back to the game'
    },
    waiting: 'Waiting for the game to begin...',
    sort: {
      button: 'Sort',
      title: 'Sort cards',
      flies: 'By flies',
      number: 'By values'
    },
    pickupHerd: 'Pickup the herd',
    pickupHerdDone: 'You pickup the herd !',
    playCard: 'Would you like to play this card ?',
    invertGameDirection: 'Would you like to invert the game direction ?',
    disconnection: 'A player has been disconnected',
    closed: 'The game have been closed unexpectedly',
    gameEnded: 'The game is over'
  },
  settings: {
    title: 'Settings',
    effectVolume: 'Sound effects volume',
    locale: 'Language',
    theme: 'Theme'
  },
  rules: {
    title: 'Rules',
    title1: 'The aim :',
    block1: {
      a: 'Have LESS FLIES.',
      b: 'It ends at less than 100 flies in total.',
      c: 'A game is played in several rounds.',
      d: 'Each card has a number of flies:'
    },
    title2: 'Preparation :',
    block2: {
      a:'Each farmer initially receives in his hand 5 cows cards, the rest constituting the DECK.',
      b:'The game starts clockwise but playing a special card allows the player to change direction if he wishes.'
    },
    title3: 'The cards :',
    block3: {
      a:'Normal cards',
      b: 'Special cards',
      c: '0 the smallest value',
      d: '16 the greatest value',
      e: '7 and 9 acrobats = to pose on cards of the same value',
      f: 'Without value = to place between 2 cards having no consecutive number',
      g: '/!\\ : On the other hand, you can not put acrobatic cows on top of it, pretending that it is number 7 or 9. Even if you insert the card clearly between a 6 and 8, it can not take that\'s worth 7 but you can not play acrobatic cows over it.'
    },
    title4: 'Course of the game :',
    block4: {
      a:'Every farmer must put a card. The number of the cow must be greater than the largest cow present in the herd or lower than the smallest cow in the herd.',
      b: 'If a player can not play: he picks up the flock and the flies go to his barn.',
      c: '/!\\ A player can pick up if he wants, even if he can play.',
      d: 'When a player picks up, it\'s up to him to play the first card.'
    },
    title5: 'End of the game :',
    block5: {
      a:'Round',
      b: 'Each game is divided into several rounds. A round ends when there is no draw: when there is no draw, the current round ends and when a player picks up, it is the end of the round, the remaining cards in the players\'s hands automatically go to their respective stables. Each fly is counted and is the score of my sleeve.',
      c: 'Game',
      d: 'As soon as a farmer REACHES or EXCEED 100 flies in total, the game ends.',
      e: 'The farmer with the smallest number of flies wins the game!'
    }
  },
  misc: {
    backToMenu: 'Back to menu',
    cancel: 'Cancel',
    validate: 'Validate',
    play: 'Play',
    yes: 'Yes',
    no: 'No',
    back: 'Back',
    from: 'From',
    to: 'To',
    quit: 'Quit'
  }
};