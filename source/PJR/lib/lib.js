/**
 * Convert any string into a camelCase notation
 * 
 * @param {String} value String to convert
 */
function toCamelCase(value) {
  return value.toLowerCase()
    .replace(/(( |-|_)+\w)/g, m => m.charAt(m.length-1).toUpperCase());
}

/**
 * Converts a condition into a regexp string matching with the condition
 * 
 * @param {string} condition Condition to convert
 * @param {Object} types Types used in variables
 */
function convertCondition(condition, types) {
  let formatedCondition = `^${condition}$`;

  // Convert optionals
  // TODO - Fix the regexp to allow nested optionals
  formatedCondition = formatedCondition.replace(/\[(?:(?<varName>[\w ]+):)?(?<varValue>[^\[]+)\]/g, (...args) => {
    let {varName, varValue} = args.pop();
    if(varName)
      return `(?<__${toCamelCase(varName)}>${varValue})?`;
    else
      return `(?:<${varValue}>)?`;
  });

  // Convert variables
  formatedCondition = formatedCondition.replace(/<(?<varName>[\w ]+):(?<varType>\w+)>/g, (...args) => {
    let {varName, varType} = args.pop();
    if(!types[varType])
      throw new TypeError(`Unknown type '${varType}'`);
    return `(?<${toCamelCase(varName)}>${types[varType]})`;
  });

  return formatedCondition;
}

/**
 * Create a message router
 * 
 * @param {MessageEvent | string} message Message to be routed
 */
function messageRouter(message) {
  if(!message || (typeof message != 'string' && typeof message.data != 'string'))
    throw new TypeError('No data found');

  if(typeof message.data == 'string')
    message = message.data;

  const TYPES = {
    ip: '((\\d|[1-9][0-9]|1\\d{2}|2[0-4]\\d|25[0-5])\\.){3}(\\d|[1-9][0-9]|1\\d{2}|2[0-4]\\d|25[0-5])',
    string: '[^\\-,]+',
    int: '(0|[1-9]\\d*)',
    boolean: '(OUI|NON)',
    direction: '(CROI|DECROI)',
    card: '(V|J|O|R|S)\\.(\\d|1[0-6]|(7|9)A|R[1-2])'
  };

  return {
    /**
     * Call the callback function if the message data match the condition
     * The condition can take variables which has a type, for instance : <name:string>
     * It can take optional parameters which can have a name : \[-f\] or if it's named \[forced:-f\]
     * 
     * @param {string} condition The condition that rules the route
     * @param {function} callback Callback function called if the message matches the condition
     */
    route(condition, callback) {
      if(typeof condition != 'string')
        throw new TypeError('Condition must be a string');
      if(typeof callback != 'function')
        throw new TypeError('Callback must be a function');

      // Once a message is routed, prevent other routes to be taken
      if(!this.routed) {
        // Route the message if the condition match
        let match = message.match(convertCondition(condition, TYPES));
        if(match) {
          let groups = match.groups;
          if(typeof groups === 'object')
            Object.keys(groups)
              .filter(key => key.startsWith('__'))
              .map(key => {
                groups[key.substr(2)] = !!groups[key];
                delete groups[key];
              });

          callback(groups);
          this.routed = true;
        }
      }

      return this;
    },

    /**
     * Represents the default route. Callback is called if not any route have been found
     * 
     * @param {function} callback Default route callback function
     */
    default(callback) {
      if(typeof callback != 'function')
        throw new TypeError('Callback must be a function');

      // Call the default callback only if not any route have been taken
      if(!this.routed) {
        callback(message);
        this.routed = true;
      }

      return this;
    },

    /**
     * Adds a temporary type to the router
     * 
     * @param {string} name Name of the type
     * @param {string} regexVal Regexp value of the type
     */
    addType(name, regexVal) {
      if(typeof name != 'string')
        throw new TypeError('Callback must be a string');
      if(typeof regexVal != 'string')
        throw new TypeError('Callback must be a string');
      if(TYPES[name])
        throw new TypeError(`Cannot override type '${name}'`);

      // Convert inner types
      regexVal = regexVal.replace(/<(?<typeName>[^>]+)>/g, (...args) => {
        let {typeName} = args.pop();
        if(!TYPES[typeName])
          throw new TypeError(`Unknown type '${typeName}'`);
        return TYPES[typeName];
      })

      TYPES[name] = regexVal;
      return this;
    },

    routed: false
  };
}

exports.messageRouter = messageRouter;