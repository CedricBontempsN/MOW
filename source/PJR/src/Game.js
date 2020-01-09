import React, {PureComponent} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {Redirect} from 'react-router-dom';
import {Cards} from './components/cards';
import {Settings as SettingsIcon} from '@material-ui/icons';
import {VolumeOff, VolumeUp, Sort} from '@material-ui/icons';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import Button from '@material-ui/core/Button';
import Card from './components/cards/Card';
import Sound from 'react-sound';
import soundFile from './assets/cowbell_sf.mp3';
import Slider from '@material-ui/lab/Slider';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import CircularProgress from '@material-ui/core/CircularProgress';
import DialogContentText from '@material-ui/core/DialogContentText';

const styles = theme => ({
    page: {
        display:'flex',
        flexDirection: 'column',
        width: '100%',
        height: '100%',
        background: 'radial-gradient(#098e2b 50%, black 150%)'
    },
    idlePage: {
        filter: 'grayscale(80%)'
    },
    menu: {
        display:'flex',
        flexDirection: 'column'
    },
    appBar: {
        height: '64px',
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between'
    },
    score: {
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center'
    },
    settings: {
        display: 'flex',
        alignItems: 'center'
    },
    board: {
        flexGrow: 1,
        alignItems: 'flex-end',
        justifyContent: 'center',
        display: 'flex'
    },
    button: {
        margin: '4px 0px'
    },
    settings: {
        padding: 24
    },
    settingsIcon: {
        color: 'white',
        cursor: 'pointer',
        '&:hover': {
            color: '#d5d9d8'
        }
    },
    cards: {
        width: '100%'
    },
    cardModalView: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center'
    },
    langContent: {
        overflow: 'hidden'
    },
    langContainer: {
        marginTop: 16,
        minWidth: '300px'
    },
    volumeContent: {
        overflow: 'hidden'
    },
    volumeContainer: {
        marginTop: 16,
        minWidth: '300px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between'
    },
    volumeSlider: {
        flexGrow: '1',
        width: 250
    },
    volumeIcon: {
        cursor: 'pointer',
        marginRight: '16px'
    },
    pickupHerd: {
        background: '#f22710',
        color: 'white'
    },
    actions: {
        display: 'flex',
        justifyContent: 'flex-end',
        padding: '0 24px'
    },
    sort: {
        color: 'white'
    },
    sortIcon: {
        marginLeft: 8
    },
    waitMessage: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        width: '100%'
    }
});

function _SortModal({classes, settings: {lang}, onExit, onSortByValue, onSortByFlies}) {
    return (
        <Dialog
            open={true}
            onClose={onExit}
        >
            <DialogTitle id="form-dialog-title">{lang.game.sort.title}</DialogTitle>
            <DialogContent>
                <div className={classes.menu}>
                    <Button variant="contained" color='primary' className={classes.button} onClick={onSortByFlies}>{lang.game.sort.flies}</Button>
                    <Button variant="contained" color='primary' className={classes.button} onClick={onSortByValue}>{lang.game.sort.number}</Button>
                    <Button variant="contained" color='default' className={classes.button} onClick={onExit}>{lang.game.settings.backToGame}</Button>
                </div>
            </DialogContent>
        </Dialog>
    );
}

const SortModal = withStyles(styles)(_SortModal);

function _SettingsModal({classes, settings: {lang}, onCancel, onExit, onSound, onLang}) {
    return (
        <Dialog
            open={true}
            onClose={onCancel}
        >
            <DialogTitle id="form-dialog-title">{lang.menu.settings}</DialogTitle>
            <DialogContent>
                <div className={classes.menu}>
                    <Button variant="contained" color='primary' className={classes.button} onClick={onLang}>{lang.game.settings.changeLang}</Button>
                    <Button variant="contained" color='primary' className={classes.button} onClick={onSound}>{lang.game.settings.soundEffects}</Button>
                    <Button variant="contained" color='secondary' className={classes.button} onClick={onExit}>{lang.misc.quit}</Button>
                    <Button variant="contained" color='primary' className={classes.button} onClick={onCancel}>{lang.game.settings.backToGame}</Button>
                </div>
            </DialogContent>
        </Dialog>
    );
}

const SettingsModal = withStyles(styles)(_SettingsModal);


class _CardModal extends PureComponent {

    playCard = () => {
        this.props.onPlay(this.props.card);
    }

    render() {
        const {classes, settings: {lang}, card, onCancelPlayCard} = this.props;
        return (
            <Dialog
                open={true}
            >
                <DialogTitle id="form-dialog-title">{lang.game.playCard}</DialogTitle>
                <DialogContent>
                    <div className={classes.cardModalView}>
                        <Card card={card} value={card.value} type={card.type}/>
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button variant="contained" color='primary' onClick={this.playCard}>{lang.misc.play}</Button>
                    <Button variant="contained" onClick={onCancelPlayCard}>{lang.misc.cancel}</Button>
                </DialogActions>
            </Dialog>
        );
    }
}

const CardModal = withStyles(styles)(_CardModal);


class _ReverseModal extends PureComponent {

    handleReverse = () => {
        this.props.onPlay(this.props.card, true);
    }

    handlePlay = () => {
        this.props.onPlay(this.props.card, false);
    }

    render() {
        const {classes, settings: {lang}, card, onCancelPlayCard} = this.props;
        return (
            <Dialog
                open={true}
            >
                <DialogTitle id="form-dialog-title">{lang.game.invertGameDirection}</DialogTitle>
                <DialogContent>
                    <div className={classes.cardModalView}>
                        <Card card={card} value={card.value} type={card.type}/>
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button variant="contained" color='primary' onClick={this.handleReverse}>{lang.misc.yes}</Button>
                    <Button variant="contained" color='primary' onClick={this.handlePlay}>{lang.misc.no}</Button>
                    <Button variant="contained" onClick={onCancelPlayCard}>{lang.misc.cancel}</Button>
                </DialogActions>
            </Dialog>
        );
    }
}

const ReverseModal = withStyles(styles)(_ReverseModal);
class _LangModal extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            locale: props.settings.locale,
            availableLocales: props.settings.availableLocales
        };
    }

    handleLocaleChange = (event, value) => {
        const {settings} = this.props;

        settings.locale = event.target.value;
        this.setState({locale: event.target.value});
    }

    render() {
        const {classes, settings: {lang}, onExit, onClose} = this.props;
        const {locale, availableLocales} = this.state;

        return (
            <Dialog
                open={true}
                onClose={() => {
                    onExit();
                    onClose();
                }}
            >
                <DialogTitle id="form-dialog-title">{lang.settings.locale}</DialogTitle>
                <DialogContent className={classes.langContent}>
                    <FormControl className={`${classes.langContainer} ${classes.formControl}`}>
                        <Select
                            value={locale}
                            onChange={this.handleLocaleChange}
                            inputProps={{
                                name: lang.settings.local,
                                id: 'local',
                            }}
                        >
                            {availableLocales.map((locale) => (
                                <MenuItem key={locale.code} value={locale.code}>{locale.name}</MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button variant="contained" onClick={onExit}>{lang.misc.back}</Button>
                </DialogActions>
            </Dialog>
        );
    }
}

const LangModal = withStyles(styles)(_LangModal);
class _VolumeModal extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            volume: props.settings.effectVolume
        };
    }

    handleVolumeChange = (event, value) => {
        const {settings} = this.props;
        
        settings.effectVolume = value;
        this.setState({volume: value});
    }

    handleMute = () => {
        const {settings} = this.props;
        const {volume} = this.state;
    
        settings.effectVolume = Math.max(0, -volume);
        this.setState({volume: -volume});
    }

    render() {
        const {classes, settings: {lang}, onExit, onClose} = this.props;
        const {volume} = this.state;

        return (
            <Dialog
                open={true}
                onClose={() => {
                    onExit();
                    onClose();
                }}
            >
                <DialogTitle id="form-dialog-title">{lang.settings.effectVolume}</DialogTitle>
                <DialogContent className={classes.volumeContent}>
                    <div className={classes.volumeContainer}>
                        {volume <= 0 ? <VolumeOff className={classes.volumeIcon} onClick={this.handleMute}/> : <VolumeUp className={classes.volumeIcon} onClick={this.handleMute}/>}
                        <div className={classes.volumeSlider}>
                            <Slider
                                min={0}
                                max={1}
                                step={.01}
                                onChange={this.handleVolumeChange}
                                value={volume} />
                        </div>
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button variant="contained" onClick={onExit}>{lang.misc.back}</Button>
                </DialogActions>
            </Dialog>
        );
    }
}

const VolumeModal = withStyles(styles)(_VolumeModal);

function _WaitingModal({classes, settings, onExit}) {
    const {lang} = settings;

    return (
        <Dialog
            open={true}
        >
            <DialogTitle id="form-dialog-title">{lang.game.waiting}</DialogTitle>
            <DialogContent>
                <div className={classes.waitMessage}>
                    <CircularProgress className={classes.progress} />
                </div>
            </DialogContent>
            <DialogActions>
                <Button variant="contained" onClick={onExit}>{lang.misc.quit}</Button>
            </DialogActions>
        </Dialog>
    );
}

const WaitingModal = withStyles(styles)(_WaitingModal);

function _DisconnectModal({classes, settings, onExit}) {
    const {lang} = settings;

    return (
        <Dialog
            open={true}
        >
            <DialogTitle id="form-dialog-title">{lang.game.disconnection}</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    {lang.game.gameEnded}
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button variant="contained" onClick={onExit}>{lang.misc.quit}</Button>
            </DialogActions>
        </Dialog>
    );
}

const DisconnectModal = withStyles(styles)(_DisconnectModal);

function _CloseModal({classes, settings, onExit}) {
    const {lang} = settings;

    return (
        <Dialog
            open={true}
        >
            <DialogTitle id="form-dialog-title">{lang.game.closed}</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    {lang.game.gameEnded}
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button variant="contained" onClick={onExit}>{lang.misc.quit}</Button>
            </DialogActions>
        </Dialog>
    );
}

const CloseModal = withStyles(styles)(_CloseModal);

function _MessageModal({classes, message}) {
    return (
        <Dialog
            open={true}
        >
            <DialogContent>
                <div className={classes.message}>
                    {message}
                </div>
            </DialogContent>
        </Dialog>
    );
}

const MessageModal = withStyles(styles)(_MessageModal);

function PlaySound({path, onEnd, volume = 0}) {
    const v = Math.round((volume * 100));

    return (<Sound
        url={path}
        playStatus={Sound.status.PLAYING}
        playFromPosition={0}
        volume={v}
        onFinishedPlaying={onEnd}
    />);
}

function getValue(vA) {
    if (vA === '9A') {
        vA = 9;
    } else if (vA === '7A') {
        vA = 7;
    } else if (vA[0] === 'R') {
        vA = 0;
    }

    return parseInt(vA);
}

function getValues(a, b) {
    return {vA: getValue(a.value), vB: getValue(b.value)};
}
class Game extends PureComponent {
    
    constructor(props){
        super(props);
        this.state = {
            waitingJoin: true,
            displaySettings: false,
            redirect: null,
            cards: [],
            currentPlayer: null
        };
    }

    componentDidMount() {
        const {manager} = this.props;
        const game = manager.getCurrentGame();

        if (game) {
            game.on('start', () => {
                this.setState({waitingJoin: false});
            });
            game.on('handUpdate', (cards) => {
                this.setState({cards, playSound: true});
            });
            game.on('newTurn', (player) => {
                if(game.isAllowedToPlay()) {
                    this.setState({playSound: true, currentlyPlaying: true});
                }else {
                    this.setState({currentlyPlaying: false});
                }
                this.setState({currentPlayer: player, playableCards: game.getPlayableCards()});
            })
            game.on('close', () => {
                this.setState({close: true});
            });
            game.on('disconnection', () => {
                this.setState({disconnected: true});
            });
        } else {
            this.setState({redirect: {path: '/'}});
        }
    }
    
    handleSettings = () => {
        this.setState({displaySettings: true});
    }
    
    handleCancel = () => {
        this.setState({displaySettings: false});
    }

    handleSelectCard = (card) => {
        this.setState({selectedCard: card});
    }
    
    handleCancelPlayCard = () => {
        this.setState({selectedCard: null, showReverse: false});
    }
    
    handlePlayCard = (card, reverse) => {
        const {manager} = this.props;
        const game = manager.getCurrentGame();

        if (game && card.type !== card.SPECIAL) {
            game.play(card);
        } else if (game && card.type === card.SPECIAL) {
            game.play(card, reverse);
        } else {
            this.setState({redirect: {path: '/'}});
        }
        this.setState({selectedCard: null, showReverse: false});
    }

    handlePlay = (card) => {
        if (card.type === card.SPECIAL) {
            this.setState({showReverse: true});
        } else {
            this.handlePlayCard(card);
        }
    }

    handleExit = () => {
        const {manager} = this.props;

        if (manager) {
            manager.destroy();
            this.setState({redirect: {path: '/join'}});
        }
    }

    handlePickupHerd = () => {
        const {manager} = this.props;
        const game = manager.getCurrentGame();

        if (game) {
            game.pickupHerd();
            this.setState({displayPickupHerd: true});
            setTimeout(() => this.setState({displayPickupHerd: false}), 2000);
        }
    }

    handleMuteSound = () => {
        this.setState({playSound: false});
    }

    handleLangSettings = () => {
        this.setState({langSettings: true, displaySettings: false});
    }

    handleLangSettingsExit = () => {
        this.setState({langSettings: false, displaySettings: true});
    }

    handleSoundSettings = () => {
        this.setState({volumeSettings: true, displaySettings: false});
    }

    handleSoundSettingsExit = () => {
        this.setState({volumeSettings: false, displaySettings: true});
    }

    handleDisplaySort = () => {
        this.setState({displaySort: true});
    }

    handleSortExit = () => {
        this.setState({displaySort: false});
    }

    handleSortByFlies = () => {
        const {cards} = this.state;

        this.setState({displaySort: false, cards: [...cards.sort((a, b) => b.flyCount > a.flyCount ? -1 : 1)]});
    }

    handleSortByValue = () => {
        const {cards} = this.state;

        this.setState({displaySort: false,  cards: [...cards.sort((a, b) => {
            const {vA, vB} = getValues(a, b);

            return vB > vA ? -1 : 1;
        })]});
    }

    render() {
        const {classes, settings} = this.props;
        const {displaySort, waitingJoin, disconnected, closed, langSettings, volumeSettings, redirect, cards, playableCards,
            displaySettings, selectedCard, showReverse, displayPickupHerd, playSound, currentlyPlaying} = this.state;
        const {lang, effectVolume} = settings;

        const game = this.props.manager.getCurrentGame();
        const herdEmpty = game && game.isHerdEmpty();

        if (redirect) {
            return <Redirect to={redirect.path}/>
        }

        return (
            <div className={`${classes.page} ${!currentlyPlaying ? classes.idlePage : ''}`}>
                <div className={classes.appBar}>
                    <div className={classes.score}></div>
                    <Button variant="contained" className={classes.pickupHerd} disabled={!currentlyPlaying || herdEmpty} onClick={this.handlePickupHerd}>{lang.game.pickupHerd}</Button>
                    <div className={classes.settings}><SettingsIcon className={classes.settingsIcon} onClick={this.handleSettings}/></div>
                </div>
                <div className={classes.actions}>
                    <Button variant="outlined" color="default" className={classes.sort} onClick={this.handleDisplaySort}>
                        {lang.game.sort.button}
                        <Sort className={classes.sortIcon}/>
                    </Button>
                </div>
                <div className={classes.board}>
                    <div className={classes.cards}>
                        <Cards cards={cards} playableCards={currentlyPlaying ? playableCards : []} onSelect={this.handleSelectCard}/>
                    </div>
                </div>
                {playSound && <PlaySound path={soundFile} onEnd={this.handleMuteSound} volume={effectVolume}/>}
                {waitingJoin && <WaitingModal settings={settings} onExit={this.handleExit}/>}
                {displaySettings && <SettingsModal onLang={this.handleLangSettings} onSound={this.handleSoundSettings} settings={settings} onCancel={this.handleCancel} onExit={this.handleExit}/>}
                {displaySort && <SortModal onSortByFlies={this.handleSortByFlies} onSortByValue={this.handleSortByValue} settings={settings} onExit={this.handleSortExit}/>}
                {langSettings && <LangModal settings={settings} onExit={this.handleLangSettingsExit} onClose={this.handleCancel}/>}
                {volumeSettings && <VolumeModal settings={settings} onExit={this.handleSoundSettingsExit} onClose={this.handleCancel}/>}
                {selectedCard && !showReverse && <CardModal settings={settings} card={selectedCard} onPlay={this.handlePlay} onCancelPlayCard={this.handleCancelPlayCard}/>}
                {selectedCard && showReverse && <ReverseModal settings={settings} card={selectedCard} onPlay={this.handlePlayCard} onCancelPlayCard={this.handleCancelPlayCard}/>}
                {displayPickupHerd && <MessageModal message={lang.game.pickupHerdDone}/>}
                {disconnected && <DisconnectModal settings={settings} onExit={this.handleExit}/>}
                {closed && <CloseModal settings={settings} onExit={this.handleExit}/>}
            </div>
        );
    }
}

Game.propTypes = {
    classes: PropTypes.object.isRequired,
    settings: PropTypes.object.isRequired,
    manager: PropTypes.object.isRequired
};

export default withStyles(styles)(Game);