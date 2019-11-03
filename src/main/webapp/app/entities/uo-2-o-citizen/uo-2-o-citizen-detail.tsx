import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './uo-2-o-citizen.reducer';
import { IUo2oCitizen } from 'app/shared/model/uo-2-o-citizen.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUo2oCitizenDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class Uo2oCitizenDetail extends React.Component<IUo2oCitizenDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { uo2oCitizenEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.uo2oCitizen.detail.title">Uo2oCitizen</Translate> [<b>{uo2oCitizenEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.uo2oCitizen.name">Name</Translate>
              </span>
            </dt>
            <dd>{uo2oCitizenEntity.name}</dd>
            <dt>
              <Translate contentKey="sampleappApp.uo2oCitizen.uo2oPassport">Uo 2 O Passport</Translate>
            </dt>
            <dd>{uo2oCitizenEntity.uo2oPassport ? uo2oCitizenEntity.uo2oPassport.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/uo-2-o-citizen" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/uo-2-o-citizen/${uo2oCitizenEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ uo2oCitizen }: IRootState) => ({
  uo2oCitizenEntity: uo2oCitizen.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Uo2oCitizenDetail);
