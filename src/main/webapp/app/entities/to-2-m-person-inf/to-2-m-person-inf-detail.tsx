import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './to-2-m-person-inf.reducer';
import { ITo2mPersonInf } from 'app/shared/model/to-2-m-person-inf.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITo2mPersonInfDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class To2mPersonInfDetail extends React.Component<ITo2mPersonInfDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { to2mPersonInfEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.to2mPersonInf.detail.title">To2mPersonInf</Translate> [<b>{to2mPersonInfEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.to2mPersonInf.name">Name</Translate>
              </span>
            </dt>
            <dd>{to2mPersonInfEntity.name}</dd>
          </dl>
          <Button tag={Link} to="/entity/to-2-m-person-inf" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/to-2-m-person-inf/${to2mPersonInfEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ to2mPersonInf }: IRootState) => ({
  to2mPersonInfEntity: to2mPersonInf.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(To2mPersonInfDetail);
