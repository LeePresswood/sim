import * as React from 'react';
import * as ReactDOM from 'react-dom';
import App from './components/app/App';
import registerServiceWorker from './assets/registerServiceWorker';
import './index.css';

ReactDOM.render(
  <App />,
  document.getElementById('root') as HTMLElement
);
registerServiceWorker();
