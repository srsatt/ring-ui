import React, {Component, ReactNode} from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';

import Icon, {IconType} from '../icon/icon';
import {Size} from '../icon/icon__constants';

import styles from './error-message.css';

export interface ErrorMessageProps {
  children?: ReactNode
  icon?: string | IconType | null | undefined
  code?: string | null | undefined
  message?: string | null | undefined
  description?: string | null | undefined
  className?: string | null | undefined
}

/**
 * @name Error Message
 */

export default class ErrorMessage extends Component<ErrorMessageProps> {
  static propTypes = {
    icon: PropTypes.oneOfType([PropTypes.string, PropTypes.elementType]),
    code: PropTypes.string,
    message: PropTypes.string,
    description: PropTypes.string,
    children: PropTypes.node,
    className: PropTypes.string
  };


  render() {
    const {className, icon, code, message, description, children} = this.props;
    const classes = classNames(styles.errorMessage, className);

    return (
      <div className={classes}>
        {icon && (
          <Icon
            className={styles.icon}
            glyph={icon}
            size={Size.Size64}
            suppressSizeWarning
          />
        )}
        <div className={styles.content}>
          <div className={styles.title}>
            {code && `${code}:`} {message}
          </div>
          {description && (
            <div className={styles.description}>
              {description}
            </div>
          )}
          {children}
        </div>
      </div>
    );
  }
}
