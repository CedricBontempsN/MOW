import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Checkbox from '@material-ui/core/Checkbox';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import {FilterList} from '@material-ui/icons';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import IconButton from '@material-ui/core/IconButton';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import Divider from '@material-ui/core/Divider';
import classNames from 'classnames';

const drawerWidth = 300;

const styles = theme => ({
  root: {
    display: 'flex',
    width: '100%',
    height: '100%'
  },
  hide: {
    display: 'none',
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
    whiteSpace: 'nowrap',
  },
  drawerOpen: {
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerClose: {
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: theme.spacing.unit * 7 + 1,
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing.unit * 9 + 1,
    },
  },
  content: {
      marginTop: 64,  
      flexGrow: 1,
      padding: theme.spacing.unit * 3,
  },
  row: {
    cursor: 'pointer'
  },
  spaceTop: {
    marginTop: 64
  }
});

class GameList extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      open: false
    };
  }
  
  handleOpen = () => {
    this.setState({open: !this.state.open});
  }

  render() {
    const {classes, parties, onSelect, settings} = this.props;
    const {open} = this.state;
    const {lang} = settings;

  return (
    <div className={classes.root}>
      <main className={classes.content}>
        <Table className={classes.table}>
        <TableHead>
          <TableRow>
            <TableCell>{lang.gamesPanel.name}</TableCell>
            <TableCell >{lang.gamesPanel.players}</TableCell>
            <TableCell >{lang.gamesPanel.bots}</TableCell>
            <TableCell >{lang.gamesPanel.state}</TableCell>
            <TableCell >{lang.gamesPanel.address}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {parties && parties.map((row, index) => {
            function handleSelect() {
              onSelect(row);
            }
            
            return (
              <TableRow hover key={`${index}${row.name}`} onClick={handleSelect} className={classes.row}>
                <TableCell component="th" scope="row">
                  {row.name}
                </TableCell>
                <TableCell >{row.connectedRP}/{row.maxRP}</TableCell>
                <TableCell >{row.connectedVP}/{row.maxVP}</TableCell>
                <TableCell >{row.status}</TableCell>
                <TableCell >{row.ip}:{row.port}</TableCell>
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
      </main>
    </div>
  );
  }
}

GameList.propTypes = {
  parties: PropTypes.array,
  onSelect: PropTypes.func.isRequired,
  classes: PropTypes.object.isRequired,
  settings: PropTypes.object.isRequired
};

export default withStyles(styles)(GameList);