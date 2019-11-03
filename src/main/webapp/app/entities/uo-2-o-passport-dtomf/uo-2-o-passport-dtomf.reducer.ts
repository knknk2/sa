import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUo2oPassportDTOMF, defaultValue } from 'app/shared/model/uo-2-o-passport-dtomf.model';

export const ACTION_TYPES = {
  SEARCH_UO2OPASSPORTDTOMFS: 'uo2oPassportDTOMF/SEARCH_UO2OPASSPORTDTOMFS',
  FETCH_UO2OPASSPORTDTOMF_LIST: 'uo2oPassportDTOMF/FETCH_UO2OPASSPORTDTOMF_LIST',
  FETCH_UO2OPASSPORTDTOMF: 'uo2oPassportDTOMF/FETCH_UO2OPASSPORTDTOMF',
  CREATE_UO2OPASSPORTDTOMF: 'uo2oPassportDTOMF/CREATE_UO2OPASSPORTDTOMF',
  UPDATE_UO2OPASSPORTDTOMF: 'uo2oPassportDTOMF/UPDATE_UO2OPASSPORTDTOMF',
  DELETE_UO2OPASSPORTDTOMF: 'uo2oPassportDTOMF/DELETE_UO2OPASSPORTDTOMF',
  RESET: 'uo2oPassportDTOMF/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUo2oPassportDTOMF>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type Uo2oPassportDTOMFState = Readonly<typeof initialState>;

// Reducer

export default (state: Uo2oPassportDTOMFState = initialState, action): Uo2oPassportDTOMFState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_UO2OPASSPORTDTOMFS):
    case REQUEST(ACTION_TYPES.FETCH_UO2OPASSPORTDTOMF_LIST):
    case REQUEST(ACTION_TYPES.FETCH_UO2OPASSPORTDTOMF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_UO2OPASSPORTDTOMF):
    case REQUEST(ACTION_TYPES.UPDATE_UO2OPASSPORTDTOMF):
    case REQUEST(ACTION_TYPES.DELETE_UO2OPASSPORTDTOMF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_UO2OPASSPORTDTOMFS):
    case FAILURE(ACTION_TYPES.FETCH_UO2OPASSPORTDTOMF_LIST):
    case FAILURE(ACTION_TYPES.FETCH_UO2OPASSPORTDTOMF):
    case FAILURE(ACTION_TYPES.CREATE_UO2OPASSPORTDTOMF):
    case FAILURE(ACTION_TYPES.UPDATE_UO2OPASSPORTDTOMF):
    case FAILURE(ACTION_TYPES.DELETE_UO2OPASSPORTDTOMF):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_UO2OPASSPORTDTOMFS):
    case SUCCESS(ACTION_TYPES.FETCH_UO2OPASSPORTDTOMF_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_UO2OPASSPORTDTOMF):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_UO2OPASSPORTDTOMF):
    case SUCCESS(ACTION_TYPES.UPDATE_UO2OPASSPORTDTOMF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_UO2OPASSPORTDTOMF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/uo-2-o-passport-dtomfs';
const apiSearchUrl = 'api/_search/uo-2-o-passport-dtomfs';

// Actions

export const getSearchEntities: ICrudSearchAction<IUo2oPassportDTOMF> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_UO2OPASSPORTDTOMFS,
  payload: axios.get<IUo2oPassportDTOMF>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IUo2oPassportDTOMF> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_UO2OPASSPORTDTOMF_LIST,
    payload: axios.get<IUo2oPassportDTOMF>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUo2oPassportDTOMF> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_UO2OPASSPORTDTOMF,
    payload: axios.get<IUo2oPassportDTOMF>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUo2oPassportDTOMF> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_UO2OPASSPORTDTOMF,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IUo2oPassportDTOMF> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_UO2OPASSPORTDTOMF,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUo2oPassportDTOMF> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_UO2OPASSPORTDTOMF,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
